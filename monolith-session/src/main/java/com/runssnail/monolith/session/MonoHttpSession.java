package com.runssnail.monolith.session;

import com.runssnail.monolith.lang.UniqID;
import com.runssnail.monolith.session.attibute.AttributeConfigDO;
import com.runssnail.monolith.session.attibute.AttributesConfigManager;
import com.runssnail.monolith.session.store.SessionStore;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.*;

/**
 * mono http session ʵ��
 *
 * @author zhengwei
 * @date��2011-6-15
 */

public class MonoHttpSession implements HttpSession, Lifecycle {

    private static final Log log = LogFactory.getLog(MonoHttpSession.class);

    public static final String SESSION_ID = "sessionID";

    /**
     *
     */
    private MonoHttpServletRequest request;

    private MonoHttpServletResponse response;

    private volatile String sessionId;

    private volatile ServletContext context;

    /**
     * session ����ʱ��
     */
    private long creationTime = System.currentTimeMillis();

    private long lastAccessedTime = creationTime;

    /**
     * sessionʧЧʱ�䣬Ĭ�ϰ��Сʱ,��λ����
     */
    private int maxInactiveInterval = 1800;

    /**
     * ����SessionAttributeStore
     */
    private Map<String, SessionStore> stores;

    /**
     * ������Ϣ����
     */
    private AttributesConfigManager attributesConfigManager;

    public MonoHttpSession() {

    }

    /**
     * @param monoRequest             MonoHttpServletRequest
     * @param monoResponse            MonoHttpServletResponse
     * @param context                 ServletContext
     * @param stores                  ����SessionStore
     * @param attributesConfigManager AttributesConfigManager
     */
    public MonoHttpSession(MonoHttpServletRequest monoRequest, MonoHttpServletResponse monoResponse, ServletContext context,
                           Map<String, SessionStore> stores, AttributesConfigManager attributesConfigManager) {
        this.request = monoRequest;
        this.response = monoResponse;
        this.context = context;
        this.stores = stores;
        this.attributesConfigManager = attributesConfigManager;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public String getId() {
        return this.sessionId;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return this.context;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getAttribute(String name) {

        return getAttributeFromStore(name);
    }

    /**
     * ��store��ȡ
     *
     * @param name
     * @return
     */
    private Object getAttributeFromStore(String name) {

        return processAttribute(name, new Callback() {

            @Override
            public Object doCallback(SessionStore store, AttributeConfigDO ac) {
                return store.getAttribute(ac);
            }

        });

    }

    /**
     * @param attrName �ڲ�������
     * @param callback
     * @return
     */
    private Object processAttribute(String attrName, Callback callback) {
        AttributeConfigDO attributeConfigDO = getAttributeConfigDO(attrName);
        if (attributeConfigDO == null) {
            return null;
        }

        SessionStore store = resolveStore(attributeConfigDO);
        return callback.doCallback(store, attributeConfigDO);
    }

    /**
     * ����store
     *
     * @param attrConfig
     * @return
     */
    private SessionStore resolveStore(AttributeConfigDO attrConfig) {
        return this.stores.get(attrConfig.getStoreKey());
    }

    /**
     * �����������ƻ�ȡ������Ϣ
     */
    private AttributeConfigDO getAttributeConfigDO(String name) {
        return attributesConfigManager.getAttributeConfigDO(name);
    }

    @Override
    public Object getValue(String name) {
        return getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        Collection<String> names = attributesConfigManager.getAttributeNames();
        return Collections.enumeration(names);
    }

    @Override
    public String[] getValueNames() {
        Collection<String> names = attributesConfigManager.getAttributeNames();
        return names.toArray(new String[names.size()]);
    }

    @Override
    public void setAttribute(String name, final Object value) {

        processAttribute(name, new Callback() {

            @Override
            public Object doCallback(SessionStore store, AttributeConfigDO ac) {
                store.setAttribute(ac, value);
                return null;
            }
        });

    }

    @Override
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        setAttribute(name, null);
    }

    @Override
    public void removeValue(String name) {
        setAttribute(name, null);
    }

    @Override
    public void invalidate() {
        Collection<String> names = attributesConfigManager.getAttributeNames();
        for (String name : names) {
            setAttribute(name, null);
        }
    }

    @Override
    public boolean isNew() {
        return true;
    }

    /**
     * �ύ��ǰ���������
     */
    public void commit() {
        try {

            for (SessionStore store : stores.values()) {
                store.commit();
            }
        } catch (Exception e) {
            log.error("session commit error", e);
        }
    }

    public MonoHttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(MonoHttpServletRequest request) {
        this.request = request;
    }

    public MonoHttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(MonoHttpServletResponse response) {
        this.response = response;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    /**
     * ��ʼ��session
     */
    public void init() {

        initStores();
        fetchSessionId();
        notifyStoresSessionReady();
    }

    private void notifyStoresSessionReady() {
        // ֻ��ʼ���õ���store
        for (SessionStore store : this.stores.values()) {
            store.onSessionReady();
        }
    }

    private void fetchSessionId() {
        // �ȳ��Դ�cookie�л�ȡ
        sessionId = (String) getAttribute(SESSION_ID);

        // ���cookie��û�У��������µ�sessionId����д��cookie��
        if (StringUtils.isBlank(sessionId)) {
            sessionId = generateSessionId();
            setAttribute(SESSION_ID, sessionId);
        }
    }

    private String generateSessionId() {
        return DigestUtils.md5Hex(UniqID.getInstance().getUniqID());
    }

    /**
     * ��ʼ��stores
     */
    private void initStores() {
        for (SessionStore store : stores.values()) {
            store.init();
            store.init(this);
        }

    }

    private interface Callback {

        Object doCallback(SessionStore store, AttributeConfigDO ac);
    }

    @Override
    public void destroy() {

    }

}

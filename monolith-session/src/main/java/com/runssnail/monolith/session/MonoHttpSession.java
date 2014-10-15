package com.runssnail.monolith.session;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.log4j.Logger;

import com.runssnail.monolith.session.attibute.AttributeConfigDO;
import com.runssnail.monolith.session.attibute.AttributesConfigManager;
import com.runssnail.monolith.session.store.SessionAttributeStore;

/**
 * mono http session ʵ��
 * 
 * @author zhengwei
 * @date��2011-6-15
 */

public class MonoHttpSession implements HttpSession, Lifecycle {

    private static final Logger                log                 = Logger.getLogger(MonoHttpSession.class);

    /**
     * 
     */
    private MonoHttpServletRequest             request;

    private MonoHttpServletResponse            response;

    /**
     * servlet ����������session
     */
    private HttpSession                        srcHttpSession;

    /**
     * session ����ʱ��
     */
    private long                               creationTime;

    /**
     * sessionʧЧʱ�䣬Ĭ�ϰ��Сʱ,��λ����
     */
    private int                                maxInactiveInterval = 1800;

    /**
     * ����SessionAttributeStore
     */
    private List<SessionAttributeStore>        stores;

    private Map<String, SessionAttributeStore> storesMap;

    /**
     * ������Ϣ����
     */
    private AttributesConfigManager            attributesConfigManager;

    public MonoHttpSession() {

    }

    /**
     * @param monoRequest
     * @param monoResponse
     * @param servletContext��javax.servlet.ServletContext
     * @param stores������SessionStore
     * @param attributesConfig������������Ϣ
     */
    public MonoHttpSession(MonoHttpServletRequest monoRequest, MonoHttpServletResponse monoResponse,
                           HttpSession httpSession, List<SessionAttributeStore> stores,
                           AttributesConfigManager attributesConfigManager) {
        this.request = monoRequest;
        this.response = monoResponse;
        this.stores = stores;
        this.srcHttpSession = httpSession;
        this.attributesConfigManager = attributesConfigManager;
        this.creationTime = System.currentTimeMillis();
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public String getId() {
        return this.srcHttpSession.getId();
    }

    @Override
    public long getLastAccessedTime() {
        return this.creationTime;
    }

    @Override
    public ServletContext getServletContext() {
        return srcHttpSession.getServletContext();
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
            public Object doCallback(SessionAttributeStore store, AttributeConfigDO ac) {
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

        SessionAttributeStore store = resolveStore(attributeConfigDO);
        return callback.doCallback(store, attributeConfigDO);
    }

    /**
     * ����store
     * 
     * @param attrConfig
     * @return
     */
    private SessionAttributeStore resolveStore(AttributeConfigDO attrConfig) {
        return storesMap.get(attrConfig.getStoreKey());
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
            public Object doCallback(SessionAttributeStore store, AttributeConfigDO ac) {
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

            for (SessionAttributeStore store : stores) {
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
        return this.srcHttpSession.getId();
    }

    public List<SessionAttributeStore> getStores() {
        return stores;
    }

    /**
     * ��ʼ��session
     */
    public void init() {

        initStores();

    }

    /**
     * ��ʼ��stores
     */
    private void initStores() {
        if (stores != null) {
            storesMap = new HashMap<String, SessionAttributeStore>(stores.size());
            for (SessionAttributeStore store : stores) {
                store.init();
                store.init(this);
                storesMap.put(store.getClass().getSimpleName(), store);
            }
        }

    }

    private interface Callback {

        Object doCallback(SessionAttributeStore store, AttributeConfigDO ac);
    }

    @Override
    public void destroy() {

    }

}

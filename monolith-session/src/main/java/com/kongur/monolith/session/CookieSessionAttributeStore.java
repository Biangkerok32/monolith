package com.kongur.monolith.session;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kongur.monolith.session.attibute.AttributeConfigDO;
import com.kongur.monolith.session.attibute.AttributeDO;
import com.kongur.monolith.session.util.BlowfishUtils;

/**
 * ��cookieʵ�ֵ�session store
 * 
 * @author zhengwei
 * @date��2011-6-15
 */

public class CookieSessionAttributeStore implements SessionAttributeStore {

    private static final Logger      log       = Logger.getLogger(CookieSessionAttributeStore.class);

    /**
     * ����ѽ���������
     */
    private Map<String, AttributeDO> attributes;

    /**
     * ���δ����������
     */
    private Map<String, String>      cookies;

    /**
     * ����޸Ĺ�������
     */
    private Set<String>              dirty;

    private MonoHttpSession          monoHttpSession;

    /**
     * ���ڱ�Ǳ���ʧ��
     */
    private static final String      ERROR     = new String("ERROR");

    /**
     * ����key
     */
    public static final String       CRYPT_KEY = "com.kongur.monolith.session";

    @Override
    public Object getAttribute(AttributeConfigDO ac) {
        String key = ac.getKey();

        // �ȴ��ѽ����л�ȡ
        AttributeDO attribute = attributes.get(key);
        if (attribute == null) {
            attribute = getAttributeFromCookies(ac);

            // ���浽�ѽ���map
            attributes.put(key, attribute);
        }

        return attribute.getValue();
    }

    /**
     * ��cookie��ȡ
     * 
     * @param ac
     * @return
     */
    private AttributeDO getAttributeFromCookies(AttributeConfigDO ac) {
        String clientKey = ac.getClientKey();
        String cookieValue = cookies.get(clientKey);
        String v = decodeValue(cookieValue, ac);
        AttributeDO attribute = new AttributeDO(ac, v);
        return attribute;
    }

    /**
     * ����
     * 
     * @param value
     * @param ac
     * @return
     */
    private String decodeValue(String value, AttributeConfigDO ac) {
        if (value == null) {
            return value;
        }

        try {
            value = URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            // logger.error("����ʧ��", e);
            // ����ʧ��ʱֱ�ӷ��أ�����������
            return value;
        }

        if (ac.isEncrypt()) {
            value = BlowfishUtils.decryptBlowfish(value, CRYPT_KEY);
        }

        return value;
    }

    @Override
    public void setAttribute(AttributeConfigDO ac, Object value) {
        String key = ac.getKey();
        // XXX ע�⣬���������value�ϵ�toString()�������Ǳ���value������
        String v = value == null ? null : value.toString();
        AttributeDO attribute = new AttributeDO(ac, v);
        attributes.put(key, attribute);
        dirty.add(key); // ����޸�
    }

    @Override
    public void commit() {
        String[] originalDirty = dirty.toArray(new String[dirty.size()]);
        for (String key : originalDirty) {
            if (dirty.contains(key)) { // ��key�����Ѿ���֮ǰ�����cookie�д�����������Ҫ�ȼ���Ƿ���dirty��
                AttributeDO attribute = attributes.get(key);
                encodeAttribute(attribute);
            }
        }
    }

    /**
     * ����
     * 
     * @param attribute
     */
    private void encodeAttribute(AttributeDO attribute) {
        String value = attribute != null ? attribute.getValue() : StringUtils.EMPTY;

        value = encodeValue(value, attribute.getAttributeConfigDO());

        if (value == ERROR) {
            return;
        }

        addCookieToResponse(attribute.getAttributeConfigDO(), value, attribute == null);
    }

    /**
     * ��cookie���浽response��
     * 
     * @param ac
     * @param value
     * @param removed
     */
    private void addCookieToResponse(AttributeConfigDO ac, String value, boolean removed) {
        String name = ac.getClientKey();
        String domain = ac.getDomain();
        // XXX ɾ��cookieʱ��maxAge��Ϊ0����ԭ����ʵ��ֻ�ǽ�ֵ��Ϊ�ն���
        int maxAge = !removed ? ac.getLifeCycle() : 0;
        String path = ac.getCookiePath();
        boolean httpOnly = ac.isHttpOnly();
        addCookieToResponse(name, value, domain, maxAge, path, httpOnly);
    }

    private void addCookieToResponse(String name, String value, String domain, int maxAge, String path, boolean httpOnly) {
        MonoHttpServletResponse response = getResponse();
        MonoCookie cookie = buildCookie(name, value, domain, maxAge, path, httpOnly);
        response.addCookie(cookie);
    }

    private MonoCookie buildCookie(String name, String value, String domain, int maxAge, String path, boolean httpOnly) {
        MonoCookie cookie = new MonoCookie(name, value);
        cookie.setDomain(domain);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        return cookie;
    }

    private MonoHttpServletResponse getResponse() {
        return this.monoHttpSession.getResponse();
    }

    /**
     * ����
     * 
     * @param value
     * @param ac
     * @return
     */
    private String encodeValue(String value, AttributeConfigDO ac) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }

        if (ac.isEncrypt()) {
            value = BlowfishUtils.encryptBlowfish(value, CRYPT_KEY);
        }

        try {
            // XXX ���Ǳ��룬��ԭʵ�ֲ�ͬ��ԭʵ�������cookieʱֻ�Լ��ܵ����Ա���
            value = URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            log.error("����ʧ��", e);
            // ����ʧ��ʱ�����ش����ǣ��Ҳ����浽cookies��
            return ERROR;
        }
        return value;
    }

    @Override
    public void init(MonoHttpSession session) {
        this.monoHttpSession = session;
        attributes = new HashMap<String, AttributeDO>();
        cookies = new HashMap<String, String>();
        dirty = new HashSet<String>();
        fetchCookies();
    }

    private void fetchCookies() {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                this.cookies.put(cookie.getName(), cookie.getValue());
            }
        }
    }

    private MonoHttpServletRequest getRequest() {
        return monoHttpSession.getRequest();
    }

}

package com.kongur.monolith.session.attibute;

import java.util.Collection;

import com.kongur.monolith.session.CookieSessionAttributeStore;
import com.kongur.monolith.session.MonoHttpSession;
;

/**
 * Ĭ�ϵ�cookie���ù�����
 * 
 * @author zhengwei
 * @date��2011-6-15
 */

public class DefaultAttributesConfigManager extends AbstractAttributesConfigManager {

    public DefaultAttributesConfigManager() {

    }

    @Override
    public AttributeConfigDO getAttributeConfigDO(String cookieName) {
        return attributeConfigs.get(cookieName);
    }

    @Override
    public Collection<String> getAttributeNames() {
        return attributeConfigs.keySet();
    }

    /**
     * ��ʼ��cookie����
     */
    public void init() {
        // ���Դ�XML�ļ����ȡ
        attributeConfigs.put(MonoHttpSession.SESSION_ID, new AttributeConfigDO(MonoHttpSession.SESSION_ID, "cookie1", true,CookieSessionAttributeStore.class.getSimpleName()));
        attributeConfigs.put("name", new AttributeConfigDO("name", "cookie2", true, "CookieSessionStore"));
        attributeConfigs.put("login", new AttributeConfigDO("login", "cookie3", true, "CookieSessionStore"));
        attributeConfigs.put("prems", new AttributeConfigDO("prems", "cookie14", true, "CookieSessionStore"));
    }

}

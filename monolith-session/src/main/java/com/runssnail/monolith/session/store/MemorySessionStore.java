package com.runssnail.monolith.session.store;

import com.runssnail.monolith.session.MonoHttpSession;
import com.runssnail.monolith.session.attibute.AttributeConfigDO;

import java.util.HashMap;
import java.util.Map;

/**
 * �ñ����ڴ�ʵ�ֵ�SessionAttributeStore��������������
 * 
 * @author zhengwei
 */
public class MemorySessionStore extends AbstractSessionStore {

    private Map<String, Object> attributes = new HashMap<String, Object>();

    @Override
    public Object getAttribute(AttributeConfigDO attribute) {
        return attributes.get(attribute.getKey());
    }

    @Override
    public void setAttribute(AttributeConfigDO attribute, Object value) {
        this.attributes.put(attribute.getClientKey(), value);
    }

    @Override
    public void commit() {

    }

    @Override
    public void init(MonoHttpSession session) {

    }

}

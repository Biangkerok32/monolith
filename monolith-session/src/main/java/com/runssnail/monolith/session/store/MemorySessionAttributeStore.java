package com.runssnail.monolith.session.store;

import java.util.HashMap;
import java.util.Map;

import com.runssnail.monolith.session.MonoHttpSession;
import com.runssnail.monolith.session.attibute.AttributeConfigDO;

/**
 * �ñ����ڴ�ʵ�ֵ�SessionAttributeStore��������������
 * 
 * @author zhengwei
 */
public class MemorySessionAttributeStore extends AbstractSessionAttributeStore {

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

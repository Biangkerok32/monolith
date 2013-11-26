package com.kongur.monolith.session.store;

import com.kongur.monolith.session.Lifecycle;
import com.kongur.monolith.session.MonoHttpSession;
import com.kongur.monolith.session.attibute.AttributeConfigDO;

/**
 * session���ݴ洢
 * 
 * @author zhengwei
 * @date��2011-6-15
 */

public interface SessionAttributeStore extends Lifecycle {

    /**
     * ��ȡ����ֵ
     * 
     * @param attribute
     * @return
     */
    public Object getAttribute(AttributeConfigDO attribute);

    /**
     * ��������ֵ
     * 
     * @param attribute
     * @param value
     */
    public void setAttribute(AttributeConfigDO attribute, Object value);

    /**
     * �ύ�޸�
     */
    public void commit();

    /**
     * ��ʼ��
     * 
     * @param session
     */
    public void init(MonoHttpSession session);

}

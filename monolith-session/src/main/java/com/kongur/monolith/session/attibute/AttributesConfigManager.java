package com.kongur.monolith.session.attibute;

import java.util.Collection;


/**
 * 
 * cookie ���ù�����
 * 
 * @author zhengwei
 * @date��2011-6-15
 * 
 */
public interface AttributesConfigManager {
    
    /**
     * ������������ȡ��������
     * 
     * @param attributeName
     * @return
     */
    AttributeConfigDO getAttributeConfigDO(String attributeName);

    /**
     * ��ȡ��ǰ����������
     * 
     * @return
     */
    Collection<String> getAttributeNames();
    
}

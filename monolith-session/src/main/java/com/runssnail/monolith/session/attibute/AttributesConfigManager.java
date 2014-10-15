package com.runssnail.monolith.session.attibute;

import java.util.Collection;

import com.runssnail.monolith.session.Lifecycle;


/**
 * 
 * cookie ���ù�����
 * 
 * ��ͨ��spring���ã�Ҳ���Դ�XML�ļ����ȡ��
 * 
 * @author zhengwei
 * @date��2011-6-15
 * 
 */
public interface AttributesConfigManager extends Lifecycle {
    
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

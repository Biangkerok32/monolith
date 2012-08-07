package com.kongur.monolith.event;


/**
 * ֧�ּ����������¼����ͺ���������ƥ���¼�
 * 
 * @author zhengwei
 *
 */
public interface SmartSystemListener<E extends SystemEvent> extends SystemListener<E> {

    /**
     * �Ƿ�֧�ָ������¼�����
     */
    boolean supportsEventType(Class<? extends SystemEvent> eventType);

    /**
     * �Ƿ�֧�ָ�����ԭ����
     */
    boolean supportsSourceType(Class<?> sourceType);

}

package com.runssnail.monolith.event;


/**
 * ֧�ּ����������¼����ͺ���������ƥ���¼�
 * 
 * @author zhengwei
 *
 */
public interface SmartEventListener<E extends Event> extends EventListener<E> {

    /**
     * �Ƿ�֧�ָ������¼�����
     */
    boolean supportsEventType(Class<? extends Event> eventType);

    /**
     * �Ƿ�֧�ָ�����ԭ����
     */
    boolean supportsSourceType(Class<?> sourceType);

}

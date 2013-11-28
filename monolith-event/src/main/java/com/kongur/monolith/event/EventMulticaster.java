package com.kongur.monolith.event;

/**
 * ����������ͷ����¼�
 * 
 * @author zhengwei
 * @param <T>
 */
public interface EventMulticaster<E extends Event> {

    /**
     * ��Ӽ�����
     * 
     * @param listener
     */
    void addListener(EventListener<E> listener);

    /**
     * ɾ��������
     * 
     * @param listener
     */
    void removeListener(EventListener<E> listener);

    /**
     * Ͷ���¼�
     * 
     * @param event
     */
    void multicastEvent(E event);

}

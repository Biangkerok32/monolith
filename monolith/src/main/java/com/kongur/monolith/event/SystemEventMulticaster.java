package com.kongur.monolith.event;

/**
 * ����������ͷ����¼�
 * 
 * @author zhengwei
 * @param <T>
 */
public interface SystemEventMulticaster<E extends SystemEvent> {

    /**
     * ��Ӽ�����
     * 
     * @param listener
     */
    void addListener(SystemListener<E> listener);

    /**
     * ɾ��������
     * 
     * @param listener
     */
    void removeListener(SystemListener<E> listener);

    /**
     * Ͷ���¼�
     * 
     * @param event
     */
    void multicastEvent(E event);

}

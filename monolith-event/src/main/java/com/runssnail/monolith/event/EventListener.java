package com.runssnail.monolith.event;

/**
 * �������ӿ�
 * 
 * @author zhengwei
 */
public interface EventListener<E extends Event> {

    /**
     * �����¼�
     * 
     * @param event
     */
    void onEvent(E event);

}

package com.kongur.monolith.event;

/**
 * ���׼����ӿ�
 * 
 * @author zhengwei
 */
public interface SystemListener<E extends SystemEvent> {

    /**
     * �����¼�
     * 
     * @param event
     */
    void onEvent(E event);

}

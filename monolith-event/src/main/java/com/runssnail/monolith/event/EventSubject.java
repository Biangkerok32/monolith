package com.runssnail.monolith.event;



/**
 * ����ӿ�
 * 
 * @author zhengwei
 *
 */
public interface EventSubject<E extends Event> {
    
    void addListener(EventListener<E> listener);
    
    void removeListener(EventListener<E> listener);
    
    void notifyAllListeners(E obj);

}

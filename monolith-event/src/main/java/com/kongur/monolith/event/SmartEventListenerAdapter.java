package com.kongur.monolith.event;

import org.springframework.core.GenericTypeResolver;

/**
 * @author zhengwei
 */
public class SmartEventListenerAdapter<E extends Event> implements SmartEventListener<E> {

    private EventListener<E> delegate;

    public SmartEventListenerAdapter(EventListener<E> listener) {
        this.delegate = listener;
    }

    @Override
    public void onEvent(E event) {
        delegate.onEvent(event);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean supportsEventType(Class<? extends Event> eventType) {
        
        // ����EventListener.onEvent()�����Ĳ������жϵ�ǰlistener�Ƿ�supports ��event
        Class typeArg = GenericTypeResolver.resolveTypeArgument(this.delegate.getClass(), EventListener.class);
        
        return (typeArg == null || typeArg.isAssignableFrom(eventType));
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

}

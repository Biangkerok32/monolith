package com.kongur.monolith.event;

import org.springframework.core.GenericTypeResolver;

/**
 * @author zhengwei
 */
public class SmartSystemListenerAdapter<E extends SystemEvent> implements SmartSystemListener<E> {

    private SystemListener<E> delegate;

    public SmartSystemListenerAdapter(SystemListener<E> listener) {
        this.delegate = listener;
    }

    @Override
    public void onEvent(E event) {
        delegate.onEvent(event);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean supportsEventType(Class<? extends SystemEvent> eventType) {
        
        // ����KongurListener.onEvent()�����Ĳ������жϵ�ǰlistener�Ƿ�supports ��event
        Class typeArg = GenericTypeResolver.resolveTypeArgument(this.delegate.getClass(), SystemListener.class);
        
        return (typeArg == null || typeArg.isAssignableFrom(eventType));
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

}

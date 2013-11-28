package com.kongur.monolith.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * �¼�Ͷ����, �ṩ��ӡ�ɾ���������¼�����
 * 
 * @author zhengwei
 */

public abstract class AbstractEventMulticaster<E extends Event> implements EventMulticaster<E> {

    protected final Logger                           log           = Logger.getLogger(getClass());

    /**
     * ������м�����
     */
    private final ListenerRetriever                  defaultRetriever = new ListenerRetriever();

    /**
     * key Ϊ�¼�class���� + �¼�����class����, valueΪ����������
     */
    private final Map<ListenerCacheKey, ListenerRetriever> listenerCache    = new ConcurrentHashMap<ListenerCacheKey, ListenerRetriever>();

    @Override
    public void addListener(EventListener<E> listener) {
        synchronized (this.defaultRetriever) {
            this.defaultRetriever.applicationListeners.add(listener);
            this.listenerCache.clear();
        }
    }

    @Override
    public void removeListener(EventListener<E> listener) {
        synchronized (this.defaultRetriever) {
            this.defaultRetriever.applicationListeners.remove(listener);
            this.listenerCache.clear();
        }
    }

    /**
     * ��ȡ�͵�ǰ�¼�ƥ��ļ�����
     * 
     * @param event
     * @return
     */
    protected Collection<EventListener<E>> getSystemListeners(E event) {

        ListenerCacheKey cacheKey = createCacheKey(event);

        ListenerRetriever retriever = this.listenerCache.get(cacheKey);

        if (retriever != null) {
            return retriever.applicationListeners;
        }

        retriever = new ListenerRetriever();
        List<EventListener<E>> allListeners = new ArrayList<EventListener<E>>();

        synchronized (this.defaultRetriever) {
            for (EventListener<E> listener : this.defaultRetriever.applicationListeners) {
                if (supportsEvent(listener, event)) {
                    retriever.applicationListeners.add(listener);
                    allListeners.add(listener);
                }
            }

            this.listenerCache.put(cacheKey, retriever);
        }

        return allListeners;
    }

    /**
     * �жϼ������Ƿ�֧�ֵ�ǰ�ṩ���¼�����
     * 
     * @param listener
     * @param event
     * @return
     */
    protected boolean supportsEvent(EventListener<E> listener, E event) {
        Class<? extends Event> eventType = event.getClass();
        Class<?> sourceType = event.getSource().getClass();

        SmartEventListener<E> smartListener = (listener instanceof SmartEventListener ? (SmartEventListener<E>) listener : new SmartEventListenerAdapter<E>(
                                                                                                                                                       listener));
        return (smartListener.supportsEventType(eventType) && smartListener.supportsSourceType(sourceType));
    }

    /**
     * ���� ����KEY
     * 
     * @param event
     * @return
     */
    protected ListenerCacheKey createCacheKey(E event) {
        Class<? extends Event> eventType = event.getClass();
        Class<?> sourceType = event.getSource().getClass();
        return new ListenerCacheKey(eventType, sourceType);
    }

    /**
     * @author zhengwei
     */
    private class ListenerRetriever {

        public final Set<EventListener<E>> applicationListeners;

        public ListenerRetriever() {
            this.applicationListeners = new LinkedHashSet<EventListener<E>>();
        }

    }

    public Map<ListenerCacheKey, ListenerRetriever> getListenerCache() {
        return listenerCache;
    }

}

package com.runssnail.monolith.event;

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

public abstract class AbstractSystemEventMulticaster<E extends SystemEvent> implements SystemEventMulticaster<E> {

    protected final Logger                           logger           = Logger.getLogger(getClass());

    /**
     * ������м�����
     */
    private final ListenerRetriever                  defaultRetriever = new ListenerRetriever();

    /**
     * key Ϊ�¼�class���� + �¼�����class����, valueΪ����������
     */
    private final Map<ListenerCacheKey, ListenerRetriever> listenerCache    = new ConcurrentHashMap<ListenerCacheKey, ListenerRetriever>();

    @Override
    public void addListener(SystemListener<E> listener) {
        synchronized (this.defaultRetriever) {
            this.defaultRetriever.applicationListeners.add(listener);
            this.listenerCache.clear();
        }
    }

    @Override
    public void removeListener(SystemListener<E> listener) {
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
    protected Collection<SystemListener<E>> getSystemListeners(E event) {

        ListenerCacheKey cacheKey = createCacheKey(event);

        ListenerRetriever retriever = this.listenerCache.get(cacheKey);

        if (retriever != null) {
            return retriever.applicationListeners;
        }

        retriever = new ListenerRetriever();
        List<SystemListener<E>> allListeners = new ArrayList<SystemListener<E>>();

        synchronized (this.defaultRetriever) {
            for (SystemListener<E> listener : this.defaultRetriever.applicationListeners) {
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
    protected boolean supportsEvent(SystemListener<E> listener, E event) {
        Class<? extends SystemEvent> eventType = event.getClass();
        Class<?> sourceType = event.getSource().getClass();

        SmartSystemListener<E> smartListener = (listener instanceof SmartSystemListener ? (SmartSystemListener<E>) listener : new SmartSystemListenerAdapter<E>(
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
        Class<? extends SystemEvent> eventType = event.getClass();
        Class<?> sourceType = event.getSource().getClass();
        return new ListenerCacheKey(eventType, sourceType);
    }

    /**
     * @author zhengwei
     */
    private class ListenerRetriever {

        public final Set<SystemListener<E>> applicationListeners;

        public ListenerRetriever() {
            this.applicationListeners = new LinkedHashSet<SystemListener<E>>();
        }

    }

    public Map<ListenerCacheKey, ListenerRetriever> getListenerCache() {
        return listenerCache;
    }

}

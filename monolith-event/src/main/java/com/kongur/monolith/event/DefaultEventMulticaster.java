package com.kongur.monolith.event;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * �¼�Ͷ����
 * 
 * @author zhengwei
 */
public class DefaultEventMulticaster extends AbstractEventMulticaster<Event> {

    /**
     * �첽�¼�Ͷ���õ��̳߳�
     */
    private Executor executor;

    public DefaultEventMulticaster() {
    }

    public DefaultEventMulticaster(Executor executor) {
        this.executor = executor;
    }

    /**
     * ��ʼ��
     */
    public void init() {
        if (this.executor == null) {
            this.executor = Executors.newSingleThreadExecutor();
        }
    }

    @Override
    public void multicastEvent(final Event event) {
        for (final EventListener<Event> listener : getEventListeners(event)) {
            try {
                exeListener(listener, event);
            } catch (Exception e) {
                log.error("execute listener error, event=" + event + ", listener=" + listener, e);
            }

        }
    }

    private void exeListener(final EventListener<Event> listener, final Event event) {

        if (executor != null) {

            executor.execute(new Runnable() {

                public void run() {
                    try {
                        listener.onEvent(event);
                    } catch (Exception e) {
                        log.error("multicast event error", e);
                    }
                }
            });

        } else {
            listener.onEvent(event);
        }

    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

}

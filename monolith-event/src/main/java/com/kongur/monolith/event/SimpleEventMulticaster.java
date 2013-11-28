package com.kongur.monolith.event;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * �¼�Ͷ����
 * 
 * @author zhengwei
 */
public class SimpleEventMulticaster extends AbstractEventMulticaster<Event> {

    /**
     * �첽�¼�Ͷ���õ��̳߳�
     */
    private Executor executor;

    public SimpleEventMulticaster() {
    }

    public SimpleEventMulticaster(Executor executor) {
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
        for (final EventListener<Event> listener : getSystemListeners(event)) {

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
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

}

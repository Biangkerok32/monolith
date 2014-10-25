package com.runssnail.monolith.event;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * �¼�Ͷ����
 * 
 * @author zhengwei
 */
public class SimpleSystemEventMulticaster extends AbstractSystemEventMulticaster<SystemEvent> {

    private static final Executor DEFAULT_EXECUTOR = Executors.newSingleThreadExecutor();

    /**
     *  �첽�¼�Ͷ���õ��̳߳�
     */
    private Executor              executor;

    public SimpleSystemEventMulticaster() {
        this.executor = DEFAULT_EXECUTOR;
    }

    public SimpleSystemEventMulticaster(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void multicastEvent(final SystemEvent event) {
        for (final SystemListener<SystemEvent> listener : getSystemListeners(event)) {

            if (executor != null) {
                
                executor.execute(new Runnable() {

                    public void run() {
                        listener.onEvent(event);
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

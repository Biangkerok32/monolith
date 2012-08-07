package com.kongur.monolith.event;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 创建SystemEventMulticaster
 * 
 * @author zhengwei
 */
public class SystemEventMulticasterFactoryBean implements FactoryBean<SystemEventMulticaster<SystemEvent>>, ApplicationContextAware {

    private final Logger                      logger      = Logger.getLogger(getClass());

    /**
     * 具体的SystemEventMulticaster实例类型
     */
    private Class<?>                          clazz;

    /**
     * 配置的监听器
     */
    private List<SystemListener<SystemEvent>> listeners;

    /**
     * 是否自动收集监听器，如果为true, 那么会从容器中自动获取类型为SmartSystemListener的所有监听器，并注册到SystemEventMulticaster里面，默认为true
     */
    private boolean                           autoCollect = true;

    private ApplicationContext                applicationContext;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SystemEventMulticaster<SystemEvent> getObject() throws Exception {
        if (clazz == null) {
            // throw new RuntimeException("please set the property of 'clazz', type of SystemEventMulticaster");
            logger.warn("the property of clazz has not be setted, the default value will be setted[SimpleSystemEventMulticaster.class]!");
            clazz = SimpleSystemEventMulticaster.class;
        }

        SystemEventMulticaster<SystemEvent> systemEventMulticaster = (SystemEventMulticaster<SystemEvent>) clazz.newInstance();

        if (listeners != null) {
            for (SystemListener<SystemEvent> listener : listeners) {
                systemEventMulticaster.addListener(listener);
            }
        }

        if (autoCollect) {
            Map<String, SmartSystemListener> listeners = applicationContext.getBeansOfType(SmartSystemListener.class);
            if (listeners != null && !listeners.isEmpty()) {
                for (Entry<String, SmartSystemListener> entry : listeners.entrySet()) {
                    systemEventMulticaster.addListener(entry.getValue());
                }
            }

        }

        return systemEventMulticaster;
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setClazz(Class<SystemEventMulticaster<SystemEvent>> clazz) {
        this.clazz = clazz;
    }

    public List<SystemListener<SystemEvent>> getListeners() {
        return listeners;
    }

    public void setListeners(List<SystemListener<SystemEvent>> listeners) {
        this.listeners = listeners;
    }

    public boolean isAutoCollect() {
        return autoCollect;
    }

    public void setAutoCollect(boolean autoCollect) {
        this.autoCollect = autoCollect;
    }

}

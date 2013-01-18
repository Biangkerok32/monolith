package com.kongur.monolith.event;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ����SystemEventMulticaster
 * 
 * @author zhengwei
 */
public class SystemEventMulticasterFactoryBean implements FactoryBean<SystemEventMulticaster<SystemEvent>>, ApplicationContextAware, InitializingBean {

    private final Logger                        logger      = Logger.getLogger(getClass());

    /**
     * �����SystemEventMulticasterʵ������
     */
    private Class<?>                            clazz;

    private SystemEventMulticaster<SystemEvent> systemEventMulticaster;

    /**
     * ���õļ�����
     */
    private List<SystemListener<SystemEvent>>   listeners;

    /**
     * �Ƿ��Զ��ռ������������Ϊtrue, ��ô����������Զ���ȡ����ΪSystemListener�����м���������ע�ᵽSystemEventMulticaster���棬Ĭ��Ϊtrue
     */
    private boolean                             autoCollect = true;

    private ApplicationContext                  applicationContext;

    @Override
    public SystemEventMulticaster<SystemEvent> getObject() throws Exception {
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void afterPropertiesSet() throws Exception {
        if (clazz == null) {
            // throw new RuntimeException("please set the property of 'clazz', type of SystemEventMulticaster");
            logger.warn("the property of clazz has not be setted, the default value will be setted[SimpleSystemEventMulticaster.class]!");
            clazz = SimpleSystemEventMulticaster.class;
        }

        systemEventMulticaster = (SystemEventMulticaster<SystemEvent>) clazz.newInstance();

        if (listeners != null) {
            for (SystemListener<SystemEvent> listener : listeners) {
                systemEventMulticaster.addListener(listener);
            }
        }

        if (autoCollect) {
            Map<String, SystemListener> listeners = applicationContext.getBeansOfType(SystemListener.class);

            if (logger.isDebugEnabled()) {
                logger.debug("auto-collect find " + (listeners != null ? listeners.size() : 0) + " listeners.");
            }

            if (listeners != null && !listeners.isEmpty()) {
                for (Entry<String, SystemListener> entry : listeners.entrySet()) {
                    systemEventMulticaster.addListener(entry.getValue());
                }
            }

        }
    }

}

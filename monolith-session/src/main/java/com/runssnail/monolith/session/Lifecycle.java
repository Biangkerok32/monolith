package com.runssnail.monolith.session;

/**
 * �������ڽӿ�
 * 
 * @author zhengwei
 */
public interface Lifecycle {

    /**
     * ��ʼ��
     */
    void init();

    /**
     * ����
     */
    void destroy();

}

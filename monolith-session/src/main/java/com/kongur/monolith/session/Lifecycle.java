package com.kongur.monolith.session;

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

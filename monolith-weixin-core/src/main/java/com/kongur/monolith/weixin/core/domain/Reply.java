package com.kongur.monolith.weixin.core.domain;

/**
 * �ظ�����
 * 
 * @author zhengwei
 * @date 2014��2��26��
 */
public interface Reply {

    /**
     * �Ƿ���������
     * 
     * @return
     */
    boolean isActive();

    /**
     * �Ƿ��ı�����
     * 
     * @return
     */
    boolean isText();

    /**
     * ����
     * 
     * @return
     */
    String getType();

    /**
     * ��Դ����
     * 
     * @return
     */
    boolean isResource();

}
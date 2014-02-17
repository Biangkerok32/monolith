package com.kongur.monolith.weixin.domain;

/**
 * ���յ�����Ϣ
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public interface Message {

    /**
     * ������Ϣ����
     * 
     * @return
     */
    String getMsgType();

    /**
     * ����ǩ��
     * 
     * @return
     */
    String getSignature();

    /**
     * ʱ���
     * 
     * @return
     */
    String getTimestamp();

    /**
     * �����
     * 
     * @return
     */
    String getNonce();

}

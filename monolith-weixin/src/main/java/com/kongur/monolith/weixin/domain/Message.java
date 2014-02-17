package com.kongur.monolith.weixin.domain;

import java.util.Map;

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

    /**
     * ��Ϣҵ������
     * 
     * @return
     */
    Map<String, Object> getParams();

    /**
     * ��ȡҵ������
     * 
     * @param key
     * @return
     */
    String getParamString(String key);

    /**
     * ��ȡҵ������
     * 
     * @param key
     * @return
     */
    Object getParam(String key);

}

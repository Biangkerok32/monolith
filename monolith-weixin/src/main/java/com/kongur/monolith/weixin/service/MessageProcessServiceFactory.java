package com.kongur.monolith.weixin.service;


/**
 * ��Ϣ��������������
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public interface MessageProcessServiceFactory {

    MessageProcessService createMessageProcessService(String msgType);

}

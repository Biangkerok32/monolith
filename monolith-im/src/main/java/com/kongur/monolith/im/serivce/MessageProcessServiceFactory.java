package com.kongur.monolith.im.serivce;


/**
 * ��Ϣ��������������
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public interface MessageProcessServiceFactory {

    MessageProcessService createMessageProcessService(String msgType);

}

package com.kongur.monolith.im.serivce;

import com.kongur.monolith.im.domain.Message;


/**
 * ��Ϣ��������������
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public interface MessageProcessServiceFactory {

    /**
     * ������Ӧ����Ϣ����
     * 
     * @param msgType
     * @return
     */
    MessageProcessService<Message> createMessageProcessService(String msgType);

}

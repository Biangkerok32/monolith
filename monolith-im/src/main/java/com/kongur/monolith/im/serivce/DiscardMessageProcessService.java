package com.kongur.monolith.im.serivce;

import org.springframework.stereotype.Service;

import com.kongur.monolith.im.domain.Message;
import com.kongur.monolith.im.domain.ServiceResult;

/**
 * ���Ҳ���ָ������Ϣ�������ʱ���ͻ�����������
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
@Service("discardMessageProcessService")
public class DiscardMessageProcessService extends AbstractMessageProcessService<Message> {

    @Override
    protected void doProcess(Message msg, ServiceResult<String> result) {
        // ignore

        log.warn("the message is discarded. msg=" + msg);

    }

}

package com.kongur.monolith.weixin.service;

import org.springframework.stereotype.Service;

import com.kongur.monolith.weixin.domain.Message;
import com.kongur.monolith.weixin.domain.ProcessResult;


/**
 *
 *���Ҳ���ָ������Ϣ�������ʱ���ͻ�����������
 *
 *@author zhengwei
 *
 *@date 2014-2-14	
 *
 */
@Service("discardMessageProcessService")
public class DiscardMessageProcessService extends AbstractMessageProcessService<Message> {

    @Override
    protected void doProcess(Message msg, ProcessResult result) {
        // ignore
    }

}


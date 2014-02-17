package com.kongur.monolith.weixin.service.process;

import org.springframework.stereotype.Service;

import com.kongur.monolith.weixin.domain.ProcessResult;
import com.kongur.monolith.weixin.domain.message.DeveloperValidateMessage;
import com.kongur.monolith.weixin.service.AbstractMessageProcessService;

/**
 * ��������֤
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
@Service("developerValidateMessageProcessService")
public class DeveloperValidateMessageProcessService extends AbstractMessageProcessService<DeveloperValidateMessage> {

    @Override
    protected void doProcess(DeveloperValidateMessage msg, ProcessResult result) {


        result.setData(msg.getEchostr()); // ��֤ͨ����ԭ����������ַ���

    }

}

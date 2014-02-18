package com.kongur.monolith.im.weixin.service.message;

import org.springframework.stereotype.Service;

import com.kongur.monolith.im.domain.ServiceResult;
import com.kongur.monolith.im.serivce.AbstractMessageProcessService;
import com.kongur.monolith.im.weixin.domain.message.DeveloperValidateMessage;

/**
 * ��������֤
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
@Service("developerValidateMessageProcessService")
public class DeveloperValidateMessageProcessService extends AbstractMessageProcessService<DeveloperValidateMessage> {

    @Override
    protected void doProcess(DeveloperValidateMessage msg, ServiceResult<String> result) {


        result.setResult(msg.getEchostr()); // ��֤ͨ����ԭ����������ַ���

    }

}

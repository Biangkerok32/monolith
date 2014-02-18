package com.kongur.monolith.im.weixin.service.process;

import org.springframework.stereotype.Service;

import com.kongur.monolith.im.domain.ProcessResult;
import com.kongur.monolith.im.serivce.AbstractMessageProcessService;
import com.kongur.monolith.im.weixin.domain.DeveloperValidateMessage;

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

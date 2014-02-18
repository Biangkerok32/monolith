package com.kongur.monolith.im.serivce;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.kongur.monolith.im.domain.Message;
import com.kongur.monolith.im.domain.ProcessResult;

/**
 * @author zhengwei
 * @date 2014-2-14
 */
public abstract class AbstractMessageProcessService<M extends Message> implements MessageProcessService<M> {

    protected final Logger log = Logger.getLogger(getClass());

    @Resource(name = "signatureValidator")
    private SignatureValidator signatureValidator;

    @Override
    public ProcessResult process(M msg) {

        ProcessResult result = new ProcessResult();
        result.setSuccess(true);

        preProcess(msg, result);

        if (!result.isSuccess()) {
            return result;
        }

        doProcess(msg, result);

        if (!result.isSuccess()) {
            return result;
        }

        postProcess(msg, result);

        return result;
    }

    protected void postProcess(M msg, ProcessResult result) {

        if (log.isDebugEnabled()) {
            log.debug("process message finished...");
        }
    }

    /**
     * Ԥ����������ǩ����֤
     * 
     * @param msg
     * @param result
     */
    protected void preProcess(M msg, ProcessResult result) {
        if (log.isDebugEnabled()) {
            log.debug("process message start...");
        }
        
        boolean isValid = signatureValidator.validate(msg.getSignature(), msg.getTimestamp(), msg.getNonce());
        if (!isValid) {
            result.setSuccess(false);
            return ;
        }
        
        // TODO aaa-zhengwei ������ʵ����Ϣȥ�أ�
        
    }

    /**
     * ��������ҵ�� ������ʵ��
     * 
     * @param msg
     * @param result
     * @return
     */
    protected abstract void doProcess(M msg, ProcessResult result);

}

package com.kongur.monolith.weixin.service;

import com.kongur.monolith.weixin.domain.Message;
import com.kongur.monolith.weixin.domain.ProcessResult;

/**
 * ��Ϣ�������
 * <p>
 *  �����ƽ̨�յ�����Ϣ
 * </p>
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public interface MessageProcessService<M extends Message> {

    /**
     * �����յ�����Ϣ����
     * 
     * @param msg
     * @return
     */
    ProcessResult process(M msg);

}

package com.kongur.monolith.im.serivce.message;

import com.kongur.monolith.im.domain.ServiceResult;
import com.kongur.monolith.im.domain.message.Message;

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
    ServiceResult<String> process(M msg);

}

package com.kongur.monolith.im.serivce;

import com.kongur.monolith.im.domain.Message;
import com.kongur.monolith.im.domain.ServiceResult;

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

package com.kongur.monolith.im.serivce.message;

import com.kongur.monolith.im.domain.message.Message;

/**
 * ��Ϣ��֤���񣬿���֤�Ƿ��ط�
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public interface MessageValidator<M extends Message> {

    boolean validate(M msg);

}

package com.kongur.monolith.weixin.service;

import com.kongur.monolith.weixin.domain.Message;

/**
 * ��Ϣ��֤���񣬿���֤�Ƿ��ط�
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public interface MessageValidator<M extends Message> {

    boolean validate(M msg);

}

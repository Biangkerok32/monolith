package com.kongur.monolith.im.serivce;

import javax.servlet.http.HttpServletRequest;

import com.kongur.monolith.im.domain.Message;


/**
 * 
 * ������Ϣ����
 *
 *@author zhengwei
 *
 *@date 2014-2-14	
 *
 */
public interface MessageBuilder {

    Message build(HttpServletRequest req);
    
}


package com.kongur.monolith.im.weixin.domain;

import com.kongur.monolith.im.domain.AbstractMessage;


/**
 *�¼����͵���Ϣ
 *
 *@author zhengwei
 *
 *@date 2014-2-14	
 *
 */
public class EventMessage extends AbstractMessage {

    public EventMessage(String signature, String timestamp, String nonce) {
        super(signature, timestamp, nonce);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 910441650557994025L;

}


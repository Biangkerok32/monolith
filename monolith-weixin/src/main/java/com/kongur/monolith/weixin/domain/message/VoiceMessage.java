package com.kongur.monolith.weixin.domain.message;

import com.kongur.monolith.weixin.domain.AbstractMessage;


/**
 *�������͵���Ϣ
 *
 *@author zhengwei
 *
 *@date 2014-2-14	
 *
 */
public class VoiceMessage extends AbstractMessage {

    public VoiceMessage(String signature, String timestamp, String nonce) {
        super(signature, timestamp, nonce);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -4930721385706842976L;

}


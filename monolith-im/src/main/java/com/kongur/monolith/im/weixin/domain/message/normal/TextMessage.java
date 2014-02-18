package com.kongur.monolith.im.weixin.domain.message.normal;

import java.util.Map;

/**
 * �ı���Ϣ
 * 
 * @author zhengwei
 * @date 2014-2-18
 */
public class TextMessage extends NormalMessage {

    public TextMessage(String signature, String timestamp, String nonce, Map<String, Object> params) {
        super(signature, timestamp, nonce, params);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8766267454666750603L;

    /**
     * ����
     * 
     * @return
     */
    public String getContent() {
        return this.getString("Content");
    }

}

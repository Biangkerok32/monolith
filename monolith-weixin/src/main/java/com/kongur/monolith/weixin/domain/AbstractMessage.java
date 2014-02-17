package com.kongur.monolith.weixin.domain;

import com.kongur.monolith.common.DomainBase;

/**
 * @author zhengwei
 * @date 2014-2-14
 */
public abstract class AbstractMessage extends DomainBase implements Message {

    /**
     * 
     */
    private static final long serialVersionUID = -2293661716502114332L;

    /**
     * ΢�ż���ǩ��
     */
    private String signature;

    /**
     * ʱ���
     */
    private String timestamp;

    /**
     * �����
     */
    private String nonce;

    /**
     * ��Ϣ����
     */
    private String msgType;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public AbstractMessage(String signature, String timestamp, String nonce) {
        this.signature = signature;
        this.timestamp = timestamp;
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

}

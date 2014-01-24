package com.kongur.monolith.socket.message;

import java.io.Serializable;

import com.kongur.monolith.socket.message.header.UpstreamHeader;

/**
 * �Է����������ݶ���(�����Է�����Ӧ������)
 * 
 * @author zhengwei
 */
public interface UpstreamMessage extends Serializable {

    void setUpstreamHeader(UpstreamHeader header);

    UpstreamHeader getUpstreamHeader();
    
    String getTransCode();

}

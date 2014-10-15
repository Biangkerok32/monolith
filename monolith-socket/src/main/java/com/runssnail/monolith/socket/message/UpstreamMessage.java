package com.runssnail.monolith.socket.message;

import java.io.Serializable;

import com.runssnail.monolith.socket.message.header.UpstreamHeader;

/**
 * �Է����������ݶ���(�����Է�����Ӧ������)
 * 
 * @author zhengwei
 */
public interface UpstreamMessage extends Serializable {

    void setUpstreamHeader(UpstreamHeader header);

    /**
     * ����ͷ
     * 
     * @return
     */
    UpstreamHeader getUpstreamHeader();

    /**
     * ������
     * 
     * @return
     */
    String getTransCode();

}

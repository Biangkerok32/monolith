package com.runssnail.monolith.socket.message;

import java.io.Serializable;

import com.runssnail.monolith.socket.message.header.DownstreamHeader;

/**
 * �������͵����ݶ���(������������Ӧ������)
 * 
 * @author zhengwei
 */
public interface DownstreamMessage extends Serializable {

    /**
     * ���״���
     * 
     * @return
     */
    String getTransCode();

    /**
     * �Ƿ�ɹ�
     * 
     * @return
     */
    boolean isSuccess();

    /**
     * ��ȡ����ͷ
     * 
     * @return
     */
    DownstreamHeader getDownstreamHeader();

}

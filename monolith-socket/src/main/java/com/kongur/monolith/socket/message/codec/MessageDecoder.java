package com.kongur.monolith.socket.message.codec;

import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;

import com.kongur.monolith.socket.message.UpstreamMessage;
import com.kongur.monolith.socket.message.header.UpstreamHeader;

/**
 * ���Ľ�����(ҵ�����ݲ���)
 * 
 * @author zhengwei
 * @param <UM>
 */

public interface MessageDecoder<UM extends UpstreamMessage> {

    /**
     * ����UpstreamMessage
     * 
     * @param header
     * @return
     */
    UM createUpstreamMessage(UpstreamHeader header);

    /**
     * ����
     * 
     * @param bodyBuf �����岿��
     * @param header ����ͷ
     * @param decoder
     * @return
     */
    DecodeResult<UM> decode(ByteBuffer bodyBuf, UpstreamHeader header, CharsetDecoder decoder) throws CodecException;

}

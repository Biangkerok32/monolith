package com.runssnail.monolith.socket.message.header;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;

import com.runssnail.monolith.socket.message.codec.CodecException;

/**
 * ���յ���������յ���Ӧ�ı���ͷ
 * 
 * @author zhengwei
 */
public interface UpstreamHeader extends Header {

    /**
     * ����, ���յ���������յ���Ӧʱ����Ҫʵ���������
     * 
     * @param buffer ����ͷ buffer
     * @param decoder
     * @throws CharacterCodingException
     */
    public void decode(ByteBuffer buffer, CharsetDecoder decoder) throws CodecException;

}
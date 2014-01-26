package com.kongur.monolith.socket.message.header;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;

import com.kongur.monolith.socket.message.codec.CodecException;

/**
 * ���յ���������յ���Ӧ�ı���ͷ
 * 
 * @author zhengwei
 */
public interface UpstreamHeader extends Header {

    /**
     * ����, ���յ���������յ���Ӧʱ����Ҫʵ���������
     * 
     * @param header
     * @param decoder
     * @throws CharacterCodingException
     */
    public void decode(ByteBuffer header, CharsetDecoder decoder) throws CodecException;

}

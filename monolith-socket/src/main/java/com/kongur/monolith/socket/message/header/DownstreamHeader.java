package com.kongur.monolith.socket.message.header;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;

import com.kongur.monolith.socket.message.codec.CodecException;

/**
 * ��Ӧ�ͷ�������ʱ�ı���ͷ
 * 
 * @author zhengwei
 */
public interface DownstreamHeader extends Header {

    /**
     * ��Ӧ�ͷ�������ʱ���뱨��ͷ
     * 
     * @param header
     * @param encoder
     * @throws CharacterCodingException
     */
    public ByteBuffer encode(CharsetEncoder encoder) throws CodecException;

}

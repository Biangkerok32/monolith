package com.kongur.monolith.socket.message.codec;

import java.nio.charset.CharsetEncoder;

import com.kongur.monolith.socket.message.DownstreamMessage;

/**
 * ���ı����� (ҵ�����ݲ���)
 * 
 * @author zhengwei
 * @param <DM>
 */
public interface MessageEncoder<DM extends DownstreamMessage> {

    /**
     * ���뱨����(ҵ�����ݲ���)
     * 
     * @param dm
     * @param encoder
     * @return
     */
    EncodeResult encode(DM dm, CharsetEncoder encoder) throws CodecException;
}

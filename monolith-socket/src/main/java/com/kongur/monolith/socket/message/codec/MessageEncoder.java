package com.kongur.monolith.socket.message.codec;

import java.nio.charset.CharsetEncoder;


/**
 * ���ı����� (ҵ�����ݲ���)
 * 
 * @author zhengwei
 *
 * @param <DSO>
 */
public interface MessageEncoder<DSO> {

    /**
     * ���뱨����(ҵ�����ݲ���)
     * 
     * @param dso
     * @param encoder
     * @return
     */
    EncodeResult encode(DSO dso, CharsetEncoder encoder) throws CodecException;
}

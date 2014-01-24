package com.kongur.monolith.socket.message.codec;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;


/**
 * ֻ����̶����Ȳ���
 * 
 * @author zhengwei
 * @param <DSO>
 */
public abstract class AbstractFixedMessageEncoder<DSO> extends AbstractMessageEncoder<DSO> {

    @Override
    protected ByteBuffer encodeMultiBuf(DSO dso, CharsetEncoder encoder) throws CharacterCodingException {
        // ignore
        return null;
    }

}

package com.kongur.monolith.socket.message.codec;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;



/**
 * ֻ��ȡD�ζ�����������
 * 
 * @author zhengwei
 * @param <USO>
 */
public abstract class AbstractFixedMessageDecoder<USO> extends AbstractMessageDecoder<USO> {

    @Override
    protected void doDecodeMultiBuf(USO uso, ByteBuffer multBuffer, CharsetDecoder decoder, DecodeResult<USO> result)
                                                                                                                   throws CharacterCodingException {
        // ignore

    }

}

package com.kongur.monolith.socket.protocol;

import java.nio.ByteBuffer;

import com.kongur.monolith.socket.message.UpstreamMessage;
import com.kongur.monolith.socket.message.codec.CodecException;

/**
 * Э�������
 * 
 * <p>�Ƽ�A + B + C 3�ν���
 * A�Σ����������ֽڳ��� (4���ֽ�)
 * B�Σ�����ͷ(�̶�����)
 * C�Σ�������(�ɱ�)
 * </p>
 * 
 * @author zhengwei
 */
public interface ProtocolParser {

    /**
     * ����
     * 
     * @param buffer
     * @return
     */
    UpstreamMessage parse(ByteBuffer buffer) throws CodecException;

}

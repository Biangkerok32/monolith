package com.kongur.monolith.socket.protocol;

import java.nio.ByteBuffer;

import com.kongur.monolith.socket.message.UpstreamMessage;
import com.kongur.monolith.socket.message.codec.CodecException;

/**
 * Э�������
 * 
 * @author zhengwei
 *
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

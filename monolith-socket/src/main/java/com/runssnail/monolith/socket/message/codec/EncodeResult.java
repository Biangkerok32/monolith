package com.runssnail.monolith.socket.message.codec;

import java.nio.ByteBuffer;

import com.runssnail.monolith.common.result.Result;

/**
 * ������
 * 
 * @author zhengwei
 */
public class EncodeResult extends Result {

    /**
     * 
     */
    private static final long serialVersionUID = 5757692437430282356L;

    /**
     * ���������ByteBuffer
     */
    private ByteBuffer        buffer;

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

}

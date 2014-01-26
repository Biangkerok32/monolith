package com.kongur.monolith.socket.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

import com.kongur.monolith.socket.netty.DefaultProtocolReplayingDecoder.DecoderState;
import com.kongur.monolith.socket.protocol.ProtocolParser;

/**
 * Ĭ�ϵ�Э�������
 * 
 * @author zhengwei
 * @date 2014-1-26
 */
public class DefaultProtocolReplayingDecoder extends ReplayingDecoder<DecoderState> {

    /**
     * ���������ݳ���
     */
    private int            length         = -1;

    private ProtocolParser protocolParser = null;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buf, DecoderState state)
                                                                                                               throws Exception {

        switch (state) {
            case READ_LENGTH:
                this.length = buf.readInt(); // ��ȡ���ĳ���
                checkpoint(DecoderState.READ_CONTENT);
            case READ_CONTENT:
                ChannelBuffer frame = buf.readBytes(this.length);
                checkpoint(DecoderState.READ_LENGTH);
                return protocolParser.parse(frame.toByteBuffer()); // ����������Ž�������
            default:
                throw new Error("Shouldn't reach here.");
        }

    }

    public ProtocolParser getProtocolParser() {
        return protocolParser;
    }

    public void setProtocolParser(ProtocolParser protocolParser) {
        this.protocolParser = protocolParser;
    }

    enum DecoderState {

        READ_LENGTH, READ_CONTENT

    }

}

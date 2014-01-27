package com.kongur.monolith.socket.netty;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

import com.kongur.monolith.socket.message.UpstreamMessage;
import com.kongur.monolith.socket.message.codec.CodecUtils;
import com.kongur.monolith.socket.netty.DefaultProtocolReplayingDecoder.DecoderState;
import com.kongur.monolith.socket.protocol.ProtocolParser;

/**
 * Ĭ�ϵ�Э�������
 * 
 * @author zhengwei
 * @date 2014-1-26
 */
public class DefaultProtocolReplayingDecoder extends ReplayingDecoder<DecoderState> {

    protected final Logger log            = Logger.getLogger(getClass());

    /**
     * ���������ݳ���
     */
    private int            length         = -1;

    private ProtocolParser protocolParser = null;

    public DefaultProtocolReplayingDecoder() {
        super(DecoderState.READ_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buf, DecoderState state)
                                                                                                              throws Exception {

        if (log.isDebugEnabled()) {
            ChannelBuffer bufCopy = buf.copy();
            log.debug("received buffer " + bufCopy.readableBytes() + " bytes, ->"
                      + CodecUtils.getString(bufCopy.toByteBuffer()) + "<-");
        }

        switch (state) {
            case READ_LENGTH:
                this.length = buf.readInt(); // ��ȡ���ĳ���
                checkpoint(DecoderState.READ_CONTENT);
            case READ_CONTENT:
                ChannelBuffer frame = buf.readBytes(this.length);
                checkpoint(DecoderState.READ_LENGTH);
                UpstreamMessage um = protocolParser.parse(frame.toByteBuffer()); // ����������Ž���
                if (log.isDebugEnabled()) {
                    log.debug("decode " + um.getTransCode() + " successful! The UpstreamMessage=" + um);
                }

                this.length = -1;
                return um;
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

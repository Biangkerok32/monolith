package com.kongur.monolith.socket.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.kongur.monolith.socket.message.DownstreamMessage;
import com.kongur.monolith.socket.message.UpstreamMessage;
import com.kongur.monolith.socket.message.codec.CodecUtils;
import com.kongur.monolith.socket.message.codec.MessageCodecFactory;
import com.kongur.monolith.socket.protocol.ProtocolParser;

/**
 * Э�������
 * 
 * @author zhengwei
 */
public abstract class AbstractProtocolDecoder extends ProtocolDecoderAdapter {

    protected final Logger                                          logger              = Logger.getLogger(getClass());

    private static final AttributeKey                               ATTRIBUTE_KEY       = new AttributeKey(
                                                                                                           ProtocolParser.class,
                                                                                                           "parser");

    // @Resource(name="defaultMessageCodecFactory")
    private MessageCodecFactory<UpstreamMessage, DownstreamMessage> messageCodecFactory = null;

    @Override
    public void decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput output) throws Exception {

        if (logger.isDebugEnabled()) {
            IoBuffer buf = buffer.getSlice(0, buffer.limit());
            logger.debug("===================decoder received buffer ->" + CodecUtils.getString(buf.buf(), false)
                         + "<-end");

        }

        while (buffer.hasRemaining()) {
            ProtocolParser parser = (ProtocolParser) session.getAttribute(ATTRIBUTE_KEY);
            if (parser == null) {
                parser = createProtocolParser(this.messageCodecFactory);
                if (logger.isDebugEnabled()) {
                    logger.debug("decode->create ProtocolParser for :" + session);
                }
            }

            UpstreamMessage uso = parser.parse(buffer.buf());

            if (uso != null) {
                output.write(uso);
                parser = null;
                session.removeAttribute(ATTRIBUTE_KEY);
            } else {

                // û�ж�ȡ�����ͽ����ݷŵ�session��´μ�����ȡ
                session.setAttribute(ATTRIBUTE_KEY, parser);
            }

        }

    }

    /**
     * ����Э��Э����
     * 
     * @param messageCodecFactory
     * @return
     */
    protected abstract ProtocolParser createProtocolParser(MessageCodecFactory<UpstreamMessage, DownstreamMessage> messageCodecFactory);

    public void setMessageCodecFactory(MessageCodecFactory<UpstreamMessage, DownstreamMessage> messageCodecFactory) {
        this.messageCodecFactory = messageCodecFactory;
    }

}
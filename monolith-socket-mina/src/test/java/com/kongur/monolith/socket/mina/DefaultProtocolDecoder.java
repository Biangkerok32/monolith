package com.kongur.monolith.socket.mina;

import com.kongur.monolith.socket.message.DownstreamMessage;
import com.kongur.monolith.socket.message.UpstreamMessage;
import com.kongur.monolith.socket.message.codec.MessageCodecFactory;
import com.kongur.monolith.socket.mina.AbstractProtocolDecoder;
import com.kongur.monolith.socket.protocol.ProtocolParser;

/**
 * ����ǰ��Ĭ�ϵĽ�����
 * 
 * @author zhengwei
 */
// @Service("defaultProtocolDecoder")
public class DefaultProtocolDecoder extends AbstractProtocolDecoder {

    @Override
    protected ProtocolParser createProtocolParser(MessageCodecFactory<UpstreamMessage, DownstreamMessage> messageCodecFactory) {
        return new DefaultProtocolParser(messageCodecFactory);
    }

  
}

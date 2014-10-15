package com.runssnail.monolith.socket.mina;

import com.runssnail.monolith.socket.message.DownstreamMessage;
import com.runssnail.monolith.socket.message.UpstreamMessage;
import com.runssnail.monolith.socket.message.codec.MessageCodecFactory;
import com.runssnail.monolith.socket.mina.AbstractProtocolDecoder;
import com.runssnail.monolith.socket.protocol.ProtocolParser;

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

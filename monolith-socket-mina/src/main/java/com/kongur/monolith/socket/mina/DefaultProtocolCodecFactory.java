package com.kongur.monolith.socket.mina;

import javax.annotation.Resource;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * ����ǰ���õı����������
 * 
 * @author zhengwei
 */
//@Service("defaultProtocolCodecFactory")
public class DefaultProtocolCodecFactory implements ProtocolCodecFactory {

    @Resource(name="defaultProtocolEncoder")
    private ProtocolEncoder protocolEncoder;

    @Resource(name="defaultProtocolDecoder")
    private ProtocolDecoder protocolDecoder;

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return protocolDecoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return protocolEncoder;
    }

    public void setProtocolEncoder(ProtocolEncoder protocolEncoder) {
        this.protocolEncoder = protocolEncoder;
    }

    public void setProtocolDecoder(ProtocolDecoder protocolDecoder) {
        this.protocolDecoder = protocolDecoder;
    }

}

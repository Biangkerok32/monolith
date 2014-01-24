package com.kongur.monolith.socket.message.codec;

/**
 * ��������������������
 * 
 * @author zhengwei
 */
public interface MessageCodecFactory<USO, DSO> {

    MessageEncoder<DSO> getMessageEncoder(String transCode);

    MessageDecoder<USO> getMessageDecoder(String transCode);

}

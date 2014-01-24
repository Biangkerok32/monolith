package com.kongur.monolith.socket.message.header;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;

/**
 * ���յ���������յ���Ӧ�ı���ͷ
 * 
 * @author zhengwei
 */
public interface UpstreamHeader {

    /**
     * ����, ���յ���������յ���Ӧʱ����Ҫʵ���������
     * 
     * @param header
     * @param decoder
     * @throws CharacterCodingException
     */
    public void decode(ByteBuffer header, CharsetDecoder decoder) throws CharacterCodingException;

    /**
     * ���ر���ͷ�ֽڳ���
     * 
     * @return
     */
    public int getBytesLen();

    /**
     * �Ƿ���ɹ�(��Ӧ�ɹ���������ɹ�)������������������ȷ��
     * 
     * @return
     */
    public boolean isSuccess();

    /**
     * ���״���
     * 
     * @return
     */
    public String getTransCode();

    public void setTransCode(String transCode);

}

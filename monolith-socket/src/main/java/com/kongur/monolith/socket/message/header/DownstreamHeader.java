package com.kongur.monolith.socket.message.header;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;


/**
 * ��Ӧ�ͷ�������ʱ�ı���ͷ
 * 
 * @author zhengwei
 */
public interface DownstreamHeader {

    /**
     * ��Ӧ�ͷ�������ʱ���뱨��ͷ
     * 
     * @param header
     * @param encoder
     * @throws CharacterCodingException
     */
    public void encode(ByteBuffer header, CharsetEncoder encoder) throws CharacterCodingException;

    /**
     * ���״���
     * 
     * @return
     */
    public String getTransCode();

    public void setTransCode(String transCode);

    public boolean isSuccess();

    /**
     * ����ͷ�̶�����(�ֽ�)
     * 
     * @return
     */
    public int getBytesLen();

}

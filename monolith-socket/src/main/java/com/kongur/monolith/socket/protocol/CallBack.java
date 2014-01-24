package com.kongur.monolith.socket.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

/**
 * @author zhengwei
 */
public interface CallBack {

    String getBlockName();

    int getFixLen();

    /**
     * �Ƿ��Ѿ������
     * 
     * @return
     */
    boolean hasRead();

    /**
     * �Ѷ�ȡ���ֽڳ���
     * 
     * @return
     */
    int getReadBufferLen();

    void putBuffer(ByteBuffer buffer);

    void newBuffer();

    void doSuccess() throws CharacterCodingException;

}

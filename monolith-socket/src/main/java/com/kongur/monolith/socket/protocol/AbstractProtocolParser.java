package com.kongur.monolith.socket.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import org.apache.log4j.Logger;

import com.kongur.monolith.socket.buffer.ByteBuffers;
import com.kongur.monolith.socket.message.UpstreamMessage;
import com.kongur.monolith.socket.message.codec.CodecException;

/**
 * @author zhengwei
 */
public abstract class AbstractProtocolParser implements ProtocolParser {

    protected final Logger   logger              = Logger.getLogger(getClass());

    /**
     * Ĭ�ϵ�A���ֽڳ���
     */
    private static final int DEFAULT_A_BLOCK_LEN = 4;

    /**
     * A���ֽڳ���
     */
    private int              aBlockLen           = DEFAULT_A_BLOCK_LEN;

    /**
     * ���������ֽڳ���
     */
    private int              len                 = -1;

    private ByteBuffer       aBlockBuffer        = ByteBuffer.allocate(4);

    @Override
    public UpstreamMessage parse(ByteBuffer buffer) throws CodecException {

        UpstreamMessage req = null;

        try {
            int localLen = readLen(buffer);
            if (localLen != -1) {
                this.len = localLen;
                req = doParse(buffer);
            }
        } catch (CharacterCodingException e) {
            throw new CodecException(e);
        }

        return req;
    }

    /**
     * �����������ĳ���(Ĭ����ͷ4���ֽ�)
     * 
     * @param buffer
     * @return
     * @throws CharacterCodingException
     */
    protected int readLen(ByteBuffer buffer) throws CharacterCodingException {

        // return buffer.getInt();
        ReadDTO readLen = readBuffer(buffer, new CallBack() {

            @Override
            public void putBuffer(ByteBuffer buffer) {
                aBlockBuffer.put(buffer);
            }

            @Override
            public void newBuffer() {
                // ignore
            }

            @Override
            public boolean hasRead() {
                return len != -1;
            }

            @Override
            public int getFixLen() {
                return aBlockLen;
            }

            @Override
            public int getReadBufferLen() {
                return aBlockBuffer.position();
            }

            @Override
            public String getBlockName() {
                return "A";
            }

            @Override
            public void doSuccess() throws CharacterCodingException {
                aBlockBuffer.flip();
            }
        });

        return aBlockBuffer.getInt();
    }

    /**
     * ������̬�ı��Ĳ��֣�ͷ����
     * 
     * @param buffer
     * @param beginIndex
     * @param len
     * @return
     * @throws CharacterCodingException
     */
    protected ReadDTO readBuffer(ByteBuffer buffer, CallBack call) throws CharacterCodingException {

        ReadDTO read = new ReadDTO();

        if (call.hasRead()) {
            if (logger.isDebugEnabled()) {
                logger.debug("====" + call.getBlockName() + " block has read===");
            }
            call.newBuffer(); // �����س���ʱ������bodyBufferΪ��
            read.setSuccess(true);
            return read;
        }

        if (!buffer.hasRemaining()) {
            return read;
        }

        call.newBuffer();

        int currPosition = buffer.position();

        int currLen = call.getReadBufferLen();

        int len = call.getFixLen();

        if (currLen == len) {
            read.setSuccess(true);
            return read;
        }

        // ʵ�ʶ�ȡ���ֽ���
        int readBytes = 0;

        int remain = buffer.limit() - buffer.position(); // buffer ��ʣ���ֽ���
        if (currLen == 0) {
            // ˵����û���κ�����
            readBytes = len > remain ? remain : len;

        } else {
            int needBytes = len - currLen; // ����Ҫ���ֽ���
            readBytes = needBytes > remain ? remain : needBytes;
        }

        ByteBuffer needBuffer = ByteBuffers.getSlice(buffer, currPosition, readBytes); // ���ص���һ���µ�BUFFER
        call.putBuffer(needBuffer);

        buffer.position(currPosition + readBytes);

        if (call.getReadBufferLen() != len) {
            logger.warn(call.getBlockName() + " buffer has not enough bytes, this block should be " + len
                        + " bytes, but only" + call.getReadBufferLen() + " bytes");
            read.setReadBytes(readBytes);
            return read;
        }

        call.doSuccess();

        read.setReadBytes(readBytes).setSuccess(true);
        return read;
    }

    protected abstract UpstreamMessage doParse(ByteBuffer buffer) throws CharacterCodingException;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

}

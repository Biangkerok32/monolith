package com.kongur.monolith.socket.message.codec;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import com.kongur.monolith.lang.StringUtil;
import com.kongur.monolith.socket.Constants;
import com.kongur.monolith.socket.buffer.ByteBuffers;

/**
 * ���빤����
 * 
 * @author zhengwei
 */
public abstract class CodecUtils {

    public static void putString(ByteBuffer buffer, String value, CharsetEncoder encoder, String splitChar)
                                                                                                           throws CodecException {

        putString(buffer, value, encoder, splitChar, true);
    }

    /**
     * дbuffer
     * 
     * @param buffer
     * @param value
     * @param encoder
     * @param splitChar �ָ���
     * @param appendSplitChar �Ƿ�׷�ӷָ���
     * @throws CodecException
     */
    public static void putString(ByteBuffer buffer, String value, CharsetEncoder encoder, String splitChar,
                                 boolean appendSplitChar) throws CodecException {

        if (StringUtil.isNotEmpty(value)) {
            // buffer.putString(value, encoder);
            buffer.put(value.getBytes());
        }

        if (appendSplitChar && splitChar != null) {
            // buffer.putString(splitChar, encoder);
            buffer.put(splitChar.getBytes());
        }
    }

    public static void putString(ByteBuffer buffer, String value, CharsetEncoder encoder) throws CodecException {
        putString(buffer, value, encoder, null, false);
    }

    public static String alignLeft(String value, String defaultStr, int size, String padStr) {

        if (StringUtil.isBlank(value)) {
            value = defaultStr;
        }

        value = StringUtil.alignLeft(value, size, padStr);

        return value;
    }

    public static int getInt(ByteBuffer buffer) throws CodecException {
        return getInt(buffer, Constants.DEFAULT_CHARSET);
    }

    public static int getInt(ByteBuffer buffer, Charset charset) throws CodecException {

        String v = getString(buffer, charset, false);
        return Integer.valueOf(v);
    }

    public static String getString(ByteBuffer buffer) throws CodecException {
        return getString(buffer, Constants.DEFAULT_CHARSET, true);
    }

    public static String getString(ByteBuffer buffer, Charset charset) throws CodecException {
        return getString(buffer, charset, true);
    }

    public static String getString(ByteBuffer buffer, boolean trim) throws CodecException {
        return getString(buffer, Constants.DEFAULT_CHARSET, trim);
    }

    
    /**
     * ת��string
     * 
     * @param buffer ByteBuffer
     * @param charset
     * @param trim �Ƿ�ȥ��ǰ��ո�
     * @return
     * @throws CodecException
     */
    public static String getString(ByteBuffer buffer, Charset charset, boolean trim) throws CodecException {

        if (buffer.limit() == 0) {
            return null;
        }

        String v = null;

        byte[] b = new byte[buffer.limit()];

        buffer.get(b);

        v = new String(b, charset);

        if (!trim) {
            return v;
        }

        return StringUtil.trim(v);
    }

    public static String getString(ByteBuffer buffer, int startIndex, int size, CharsetDecoder decoder)
                                                                                                       throws CodecException {
        return getString(buffer, startIndex, size, decoder, true);
    }

    /**
     * ��buffer�н����ֶ�ֵ
     * 
     * @param buffer
     * @param startIndex ��ʼλ��
     * @param endIndex ����λ��
     * @param decoder
     * @param trim �Ƿ�ȥ��ǰ��ո�
     * @return
     * @throws CodecException
     */
    public static String getString(ByteBuffer buffer, int startIndex, int len, CharsetDecoder decoder, boolean trim)
                                                                                                                    throws CodecException {
        ByteBuffer dataBuffer = ByteBuffers.getSlice(buffer, startIndex, len);

        // byte[] dst = new byte[len];
        // buffer.get(dst, startIndex, len);
        // ByteBuffer dataBuffer = ByteBuffer.allocate(len);
        // dataBuffer.put(dst);
        // dataBuffer.flip();
        String v = getString(dataBuffer, Constants.DEFAULT_CHARSET, trim);

        return v;
    }

    public static Long getLong(ByteBuffer buffer, int startIdx, int size, CharsetDecoder decoder) throws CodecException {

        String v = getString(buffer, startIdx, size, decoder, false);
        if (StringUtil.isNotBlank(v)) {
            return Long.valueOf(v);
        }

        return null;
    }

    /**
     * �����buffer, �����
     * 
     * @param value
     * @param size �ֽ���
     * @param encoder
     * @return
     */
    public static ByteBuffer getBufferAlignLeft(String value, int size, CharsetEncoder encoder, byte padByte) {

        byte[] b = getAndCheckBytes(value, padByte, size);

        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.put(b);
        for (int i = 0; i < (size - b.length); i++) {
            buffer.put(padByte);
        }

        buffer.flip();
        return buffer;
    }

    /**
     * �����buffer, �����
     * 
     * @param value
     * @param size �ֽ���
     * @param encoder
     * @return
     */
    public static ByteBuffer getBufferAlignLeft(String value, int size, CharsetEncoder encoder) {

        return getBufferAlignLeft(value, size, encoder, Constants.DEFAULT_BLANK_SPACE_BYTE);
    }

    /**
     * �����buffer, �Ҷ���
     * 
     * @param value
     * @param size �ֽ���
     * @param encoder
     * @return
     */
    public static ByteBuffer getBufferAlignRight(String value, int size, CharsetEncoder encoder, byte padByte) {

        byte[] b = getAndCheckBytes(value, padByte, size);

        ByteBuffer buffer = ByteBuffer.allocate(size);

        for (int i = 0; i < (size - b.length); i++) {
            buffer.put(padByte);
        }

        buffer.put(b);

        buffer.flip();
        return buffer;
    }

    /**
     * ��ȡ�ֽ� �����鳤�ȣ������ͱ���
     * 
     * @param value
     * @param defaultByte
     * @param size
     * @return
     */
    private static byte[] getAndCheckBytes(String value, byte defaultByte, int size) {

        byte[] b = null;

        if (value == null) {
            b = new byte[] { defaultByte };
        } else {
            b = value.getBytes(Constants.DEFAULT_CHARSET);
        }

        check(value, b.length, size);

        return b;
    }

    public static ByteBuffer getBufferAlignRight(String v, int size, CharsetEncoder encoder) {
        return getBufferAlignRight(v, size, encoder, Constants.ZERO_BYTE);
    }

    private static void check(String value, int realLen, int size) {

        if (realLen > size) {
            throw new CodecException("the field bytes length is too long! value=" + value + ", shold be " + size
                                     + ", but " + realLen);
        }
    }

    public static ByteBuffer getLongBuffer(Long v, int size, CharsetEncoder encoder) {
        if (v == null) {
            v = 0L;
        }
        return getBufferAlignRight(String.valueOf(v), size, encoder, Constants.ZERO_BYTE);
    }

    public static byte[] getBytes(String v) {
        if (v != null) {
            return v.getBytes(Constants.DEFAULT_CHARSET);
        }

        return new byte[0];
    }

    public static byte[] getBytes(Long v) {
        if (v != null) {
            return v.toString().getBytes(Constants.DEFAULT_CHARSET);
        }

        return new byte[0];
    }

    public static Integer getInt(ByteBuffer buffer, int startIndex, int size, CharsetDecoder decoder)
                                                                                                     throws CodecException {
        String v = getString(buffer, startIndex, size, decoder);

        if (v == null || "".equals(v)) {
            return 0;
        }

        return Integer.valueOf(v);
    }

    public static ByteBuffer getIntBuffer(Integer v, int len, CharsetEncoder encoder) {

        if (v == null) {
            v = 0;
        }

        return getBufferAlignRight(String.valueOf(v), len, encoder);
    }

}

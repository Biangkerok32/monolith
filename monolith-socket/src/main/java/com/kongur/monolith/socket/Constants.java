package com.kongur.monolith.socket;

import java.nio.charset.Charset;

/**
 * �����ʱ�õ��ĳ���
 * 
 * @author zhengwei
 */
public class Constants {

    private Constants() {

    }

    public static final Charset DEFAULT_CHARSET                = Charset.forName("GBK");

    /**
     * ���з�
     */
    public static final String  DEFAULT_NEW_BREAK              = "\n";

    /**
     * �س�����
     */
    public static final String  DEFAULT_ENTER_NEW_BREAK        = "\r\n";

    public static final byte    DEFAULT_NEW_BREAK_BYTE         = DEFAULT_NEW_BREAK.getBytes(DEFAULT_CHARSET)[0];

    /**
     * Ĭ�ϵķָ��ֽ�
     */
    public static final byte    DEFAULT_SPLIT_BYTE             = 0x02;

    /**
     * �ָ��
     */
    public static final String  DEFAULT_SPLIT_CHAR             = new String(new byte[] { DEFAULT_SPLIT_BYTE },
                                                                            DEFAULT_CHARSET);

    /**
     * ���õ�session��Ķ���key
     */
    public static final String  RESPONSE_KEY                   = "response_key";

    /**
     * �ո�
     */
    public static final String  BLANK_SPACE                    = " ";

    /**
     * �ո�ת�����ֽ�
     */
    public static final byte    DEFAULT_BLANK_SPACE_BYTE       = BLANK_SPACE.getBytes(DEFAULT_CHARSET)[0];

    /**
     * spring��ʼ��ʱ���Զ�����ص�codec���õ������Ϊ������ID����׺����MessageCodec
     */
    public static final String  MESSAGE_CODEC                  = "MessageCodec";

    public static final String  ZERO                           = "0";

    /**
     * �㻻���ֽ�
     */
    public static final byte    ZERO_BYTE                      = ZERO.getBytes(DEFAULT_CHARSET)[0];

    /**
     * Ĭ�ϵ����ڸ�ʽ
     */
    public static final String  DEFAULT_DATE_FORMAT_STR        = "yyyyMMdd";

    /**
     * Ĭ�ϵ�ʱ���ʽ
     */
    public static final String  DEFAULT_TIME_FORMAT_STR        = "HHmmss";

    /**
     * Ĭ�ϵĴ���buffer��С
     */
    public static final int     DEFAULT_BUFFER_SIZE            = 256;

    public static final String  MESSAGE_ENCODER                = "MessageEncoder";

    public static final String  MESSAGE_DECODER                = "MessageDecoder";

}

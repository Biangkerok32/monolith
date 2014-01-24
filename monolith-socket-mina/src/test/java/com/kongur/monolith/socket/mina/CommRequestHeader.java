package com.kongur.monolith.socket.mina;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;

import com.kongur.monolith.socket.Constants;
import com.kongur.monolith.socket.message.codec.CodecUtils;
import com.kongur.monolith.socket.message.header.UpstreamHeader;

/**
 * ����ǰ���õ�����ͷ
 * 
 * ����������
 * 
 * @author zhengwei
 */
public class CommRequestHeader extends RequestHeader implements UpstreamHeader {

    public CommRequestHeader(String transCode) {
        super(transCode);
    }

    public CommRequestHeader() {

    }

    /**
     * 
     */
    private static final long serialVersionUID = -3225390310291177888L;

    /**
     * ����������
     */
    private String            exchangeNo;

    /**
     * ���д���
     */
    private String            bankNo;

    public String getExchangeNo() {
        return exchangeNo;
    }

    public void setExchangeNo(String exchangeNo) {
        this.exchangeNo = exchangeNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Override
    public void decode(java.nio.ByteBuffer buffer, CharsetDecoder decoder) throws CharacterCodingException {
        // �汾�� 2 ĿǰĬ��01
        // ���״��� 4 0001
        // ��������� 6
        // ���д��� 6
        // ������ˮ�� 20 ���㲹��
        // �������� 8 YYYYMMDD
        // ����ʱ�� 6 HH24MiSS
        // ��¼���� 8 ǰ��0

        this.setVersion(CodecUtils.getString(buffer, 0, 2, decoder));
        this.setTransCode(CodecUtils.getString(buffer, 2, 4, decoder));
        this.setExchangeNo(CodecUtils.getString(buffer, 6, 6, decoder));
        this.setBankNo(CodecUtils.getString(buffer, 12, 6, decoder));
        this.setBizNo(CodecUtils.getString(buffer, 18, 20, decoder));
        this.setTransDate(CodecUtils.getString(buffer, 38, 8, decoder));
        this.setTransTime(CodecUtils.getString(buffer, 46, 6, decoder));
        this.setFileCount(CodecUtils.getInt(buffer, 52, 8, decoder));
    }

    @Override
    public int getBytesLen() {
        return Constants.COMM_REQUEST_HEADER_BYTES_LEN;
    }

}

package com.kongur.monolith.socket.mina;

import java.nio.ByteBuffer;
import java.nio.charset.CharsetEncoder;
import java.util.Date;

import com.kongur.monolith.lang.DateUtil;
import com.kongur.monolith.lang.StringUtil;
import com.kongur.monolith.socket.message.codec.CodecException;
import com.kongur.monolith.socket.message.codec.CodecUtils;
import com.kongur.monolith.socket.message.header.DownstreamHeader;

/**
 * ������ǰ�ý�������Ӧ����ͷ, ���������� ����ϵͳ��Ӧͷ
 * 
 * @author zhengwei
 */
public class CommResponseHeader extends ResponseHeader implements DownstreamHeader {

    public CommResponseHeader(String transCode) {
        super(transCode);
    }

    public CommResponseHeader() {
    }

    /**
     * 
     */
    private static final long serialVersionUID = 7567803945784009881L;

    /**
     * ��Ӧ����ˮ��
     */
    private String            resBizNo;

    public String getResBizNo() {
        return resBizNo;
    }

    public void setResBizNo(String resBizNo) {
        this.resBizNo = resBizNo;
    }

    @Override
    public ByteBuffer encode(CharsetEncoder encoder) throws CodecException {

        // ���״��� 4 ���ܺ�
        // ������ˮ�� 20 ���㲹��
        // Ӧ����ˮ�� 20 ���㲹��
        // �������� 8 YYYYMMDD
        // ��¼���� 8 ǰ��0
        // ������ 4 0000-������������Ϊʧ��
        // ������Ϣ 60 ���������Ϣ

        ByteBuffer header = ByteBuffer.allocate(getBytesLen());
        header.put(CodecUtils.getBufferAlignLeft(this.getTransCode(), 4, encoder));
        header.put(CodecUtils.getBufferAlignLeft(this.getBizNo(), 20, encoder));
        header.put(CodecUtils.getBufferAlignLeft(this.getResBizNo(), 20, encoder));

        String transDate = this.getTransDate();
        if (StringUtil.isBlank(transDate)) {
            transDate = DateUtil.getDateTime(com.kongur.monolith.socket.Constants.DEFAULT_DATE_FORMAT_STR, new Date());
        }
        header.put(CodecUtils.getBufferAlignLeft(transDate, 8, encoder));
        header.put(CodecUtils.getIntBuffer(this.getFileCount(), 8, encoder));
        header.put(CodecUtils.getBufferAlignLeft(this.getErrorCode(), 4, encoder));
        header.put(CodecUtils.getBufferAlignLeft(this.getErrorMsg(), 60, encoder));
        header.flip();
        
        
        return header;

    }

    @Override
    public int getBytesLen() {
        return Constants.COMM_RESPONSE_HEADER_BYTES_LEN;
    }

}

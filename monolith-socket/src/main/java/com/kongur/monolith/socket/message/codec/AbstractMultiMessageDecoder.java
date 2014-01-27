package com.kongur.monolith.socket.message.codec;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;

import org.apache.commons.lang.StringUtils;

import com.kongur.monolith.lang.StringUtil;
import com.kongur.monolith.socket.message.UpstreamMessage;
import com.kongur.monolith.socket.message.UpstreamMessageSet;
import com.kongur.monolith.socket.message.header.UpstreamHeader;

/**
 * �п��ܶ�����¼���ص�Э�������
 * 
 * @author zhengwei
 */
public abstract class AbstractMultiMessageDecoder<UM extends UpstreamMessage> extends AbstractMessageDecoder<UpstreamMessageSet<UM>> {

    @Override
    public UpstreamMessageSet<UM> createUpstreamMessage(UpstreamHeader header) {
        return new UpstreamMessageSet<UM>(header);
    }

    @Override
    protected void doDecode(UpstreamMessageSet<UM> usoSet, ByteBuffer bodyBuf, CharsetDecoder decoder,
                            DecodeResult<UpstreamMessageSet<UM>> result) throws CodecException {

        try {
            String dataStr = CodecUtils.getString(bodyBuf, false);

            if (StringUtil.isEmpty(dataStr)) {
                return;
            }

            String[] lines = dataStr.split(getNewBreak(), -1);

            for (String line : lines) {
                if (StringUtils.isBlank(line)) {
                    continue;
                }

                String[] fields = line.split(getSplitChar(), -1);

                UM uso = createOneUpstreamMessage();

                doDecodeOne(uso, fields, result);

                if (!result.isSuccess()) {
                    return;
                }

                usoSet.addUpstreamMessage(uso);
            }
        } catch (Exception e) {
            throw new CodecException(e);
        }

    }

    protected abstract UM createOneUpstreamMessage();

    /**
     * �����Լ�ʵ�־���Ľ��빤��
     * 
     * @param buffer
     * @return
     */
    protected abstract void doDecodeOne(UM uso, String[] fields, DecodeResult<UpstreamMessageSet<UM>> result)
                                                                                                             throws CharacterCodingException;

}

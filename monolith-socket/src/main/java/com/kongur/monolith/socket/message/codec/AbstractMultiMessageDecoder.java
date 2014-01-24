package com.kongur.monolith.socket.message.codec;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;

import org.apache.commons.lang.StringUtils;

import com.kongur.monolith.lang.StringUtil;
import com.kongur.monolith.socket.message.UpstreamMessageSet;
import com.kongur.monolith.socket.message.header.UpstreamHeader;

/**
 * �п��ܶ�����¼���ص�Э�������
 * 
 * @author zhengwei
 */
public abstract class AbstractMultiMessageDecoder<USO> extends AbstractMessageDecoder<UpstreamMessageSet<USO>> {

    @Override
    public UpstreamMessageSet<USO> createUpstreamMessage(UpstreamHeader header) {
        return new UpstreamMessageSet<USO>(header);
    }

    @Override
    protected void doDecodeMultiBuf(UpstreamMessageSet<USO> usoSet, ByteBuffer multiBuffer, CharsetDecoder decoder,
                                    DecodeResult<UpstreamMessageSet<USO>> result) throws CharacterCodingException {

        String dataStr = CodecUtils.getString(multiBuffer, false);

        if (StringUtil.isEmpty(dataStr)) {
            return;
        }

        String[] lines = dataStr.split(getNewBreak(), -1);

        for (String line : lines) {
            if (StringUtils.isBlank(line)) {
                continue;
            }

            String[] fields = line.split(getSplitChar(), -1);

            USO uso = createOneUpStreamObject();

            doDecodeOne(uso, fields, result);

            if (!result.isSuccess()) {
                return;
            }

            usoSet.addUpstreamMessage(uso);
        }
    }

    protected abstract USO createOneUpStreamObject();

    /**
     * �����Լ�ʵ�־���Ľ��빤��
     * 
     * @param buffer
     * @return
     */
    protected abstract void doDecodeOne(USO uso, String[] fields, DecodeResult<UpstreamMessageSet<USO>> result)
                                                                                                               throws CharacterCodingException;

    @Override
    protected void doDecodeFixedBuf(UpstreamMessageSet<USO> usoSet, ByteBuffer fixBuffer, CharsetDecoder decoder,
                                    DecodeResult<UpstreamMessageSet<USO>> result) throws CharacterCodingException {
        // ignore
    }

}

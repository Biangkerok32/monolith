package com.kongur.monolith.socket.message.codec;

import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;

import com.kongur.monolith.socket.message.header.UpstreamHeader;

/**
 * ���Ľ�����(ҵ�����ݲ���)
 * 
 * @author zhengwei
 * @param <USO>
 */

public interface MessageDecoder<USO> {

    /**
     * ����UpstreamMessage
     * 
     * @param header
     * @return
     */
    USO createUpstreamMessage(UpstreamHeader header);

//    /**
//     * ����
//     * 
//     * @param fixedBuf ������������
//     * @param multiBuf ѭ����������
//     * @param header ����ͷ
//     * @param decoder
//     * @return
//     */
//    DecodeResult<USO> decode(ByteBuffer fixedBuf, ByteBuffer multiBuf, UpstreamHeader header, CharsetDecoder decoder)
//                                                                                                                     throws CodecException;

    /**
     * ����
     * 
     * @param bodyBuf �����岿��
     * @param header ����ͷ
     * @param decoder
     * @return
     */
    DecodeResult<USO> decode(ByteBuffer bodyBuf, UpstreamHeader header, CharsetDecoder decoder) throws CodecException;

}

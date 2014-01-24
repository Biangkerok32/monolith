package com.kongur.monolith.socket.mina;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;

import com.kongur.monolith.socket.message.codec.AbstractFixedMessageEncoder;
import com.kongur.monolith.socket.message.codec.CodecUtils;

/**
 * ��������������ת������ 6200
 * 
 * @author zhengwei
 */
// @Service("6200MessageEncoder")
public class EBankToExchangeMessageEncoder extends AbstractFixedMessageEncoder<TransferResponse> {

    @Override
    protected ByteBuffer doEncode(TransferResponse dso, CharsetEncoder encoder) throws CharacterCodingException {

        ByteBuffer buffer = allocateBuffer();
        // �����˺� C(30) ���пͻ������˺� M
        // �����˺� C(30) �����������˺� M
        // ���Ҵ��� C(3) CNY������� HKD���۱� USD����Ԫ M
        // ת�˽�� N(15) ��λ��ȷ���� M

        buffer.put(CodecUtils.getBufferAlignLeft(dso.getBankAccount(), 30, encoder));
        buffer.put(CodecUtils.getBufferAlignLeft(dso.getFundAccount(), 30, encoder));
        buffer.put(CodecUtils.getBufferAlignLeft(dso.getMoneyType(), 3, encoder));
        buffer.put(CodecUtils.getLongBuffer(dso.getTransAmount(), 15, encoder));

        buffer.flip();
        return buffer;
    }

}

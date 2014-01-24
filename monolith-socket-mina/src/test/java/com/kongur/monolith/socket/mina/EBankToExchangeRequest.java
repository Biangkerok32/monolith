package com.kongur.monolith.socket.mina;

import com.kongur.monolith.socket.message.header.UpstreamHeader;


/**
 * 
 * ����������ģ� ����ת������ 6200
 * 
 * @author zhengwei
 *
 */
public class EBankToExchangeRequest extends TransferRequest {

	/**
	 * bankAccount �����˺� 	(����)
	 * fundAccount �����˺� 	(����)
	 * moneyType   ���ִ���	(����)
	 * transAmount ת�˽��	(����)
	 */
    public EBankToExchangeRequest() {
        super("6200");
    }

    public EBankToExchangeRequest(UpstreamHeader header) {
        super(header);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1629882582416731835L;

}

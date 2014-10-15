package com.runssnail.monolith.socket.mina;


/**
 * 6200 ����������ģ� ����ת������
 * 
 * 
 * @author zhengwei
 *
 */
public class EBankToExchangeResponse extends TransferResponse {

	/**
	 * bankAccount �����˺� M
	 * fundAccount �����˺� M
	 * moneyType   ���ִ��� M
	 * transAmount ת�˽�� M
	 */
    public EBankToExchangeResponse() {
        super("6200");
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1438042273977910765L;

}

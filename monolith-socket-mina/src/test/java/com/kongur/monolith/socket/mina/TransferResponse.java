package com.kongur.monolith.socket.mina;


/**
 * ת������Ӧ����
 * 
 * @author zhengwei
 */
public class TransferResponse extends AbstractCommonResponse {

    public TransferResponse(String transCode) {
        super(transCode);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 3413135087316120228L;

    /**
     * �����ʺ�
     */
    private String            bankAccount;

    /**
     * �������ʽ��ʺ�
     */
    private String            fundAccount;

    /**
     * ���Ҵ���
     */
    private String            moneyType;

    /**
     * ת�˽��
     */
    private Long              transAmount;

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public Long getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Long transAmount) {
        this.transAmount = transAmount;
    }

}

package com.kongur.monolith.socket.mina;

import com.kongur.monolith.socket.message.header.UpstreamHeader;


/**
 * ת��������
 * 
 * @author zhengwei
 */
public class TransferRequest extends AbstractCommonReq {

    /**
     * 
     */
    private static final long serialVersionUID = 5014613677906979243L;

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

    /**
     * �����˺����� ���Ĵ���
     */
    private String            password;

    public TransferRequest(String transCode) {
        super(transCode);
    }

    public TransferRequest(UpstreamHeader header) {
        super(header);
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

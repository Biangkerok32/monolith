package com.runssnail.monolith.socket.mina;

/**
 * ������Ӧ
 * 
 * @author zhengwei
 */
public interface Response {

    /**
     * ���׺�
     * 
     * @return
     */
    String getTransCode();

    public String getBizNo();

    public void setBizNo(String bizNo);

    public String getTransDate();

    public void setTransDate(String transDate);

    public String getFileName();

    public void setFileName(String fileName);

    public int getFileCount();

    public void setFileCount(int fileCount);

    public String getErrorCode();

    public void setErrorCode(String errorCode);

    public String getErrorMsg();

    public void setErrorMsg(String errorMsg);
    
    /**
     * ��Ӧ�����Ƿ���ȷ������errorCode�ж�
     * 
     * @return
     */
    boolean isSuccess();

    // String getResBizNo();

}

package com.runssnail.monolith.socket.mina;

import java.io.Serializable;

import com.runssnail.monolith.common.DomainBase;

/**
 * ����ͷ
 * 
 * @author zhengwei
 */
public abstract class Header extends DomainBase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7678671306504236142L;

    /**
     * �汾��
     */
    private String            version          = "01";

    /**
     * ���״���
     */
    private String            transCode;

    /**
     * ��ˮ��
     */
    private String            bizNo;

    /**
     * ����
     */
    private String            transDate;

    /**
     * �󲹿գ������һ������
     */
    private String            fileName;

    /**
     * ��¼������ǰ��0
     */
    private int               fileCount        = 0;

    public Header() {
    }

    public Header(String transCode) {
        this.transCode = transCode;
    }

//    /**
//     * ��Ӧ�ͷ�������ʱ���뱨��ͷ
//     * 
//     * @param header
//     * @param encoder
//     * @throws CharacterCodingException
//     */
//    public abstract void encode(IoBuffer header, CharsetEncoder encoder) throws CharacterCodingException;
//
//    /**
//     * ����, ���յ���������յ���Ӧʱ����Ҫʵ���������
//     * 
//     * @param header
//     * @param decoder
//     * @throws CharacterCodingException
//     */
//    public abstract void decode(IoBuffer header, CharsetDecoder decoder) throws CharacterCodingException;

    /**
     * ͷ���ֽڳ���
     * 
     * @return
     */
    public abstract int getBytesLen();
    
    /**
     * �Ƿ���ϵͳ����
     * 
     * @return
     */
    public abstract boolean isSysError();

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isSuccess() {
        return true;
    }

    public abstract boolean isRequest();

    /**
     * �Ƿ����ж�����¼�����Э����Ӧ����
     * 
     * @return
     */
    public boolean isMultResponse() {
        return false;
    }
}

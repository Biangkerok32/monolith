package com.runssnail.monolith.socket.message.codec;

/**
 * ���Ľ����������
 * 
 * @author zhengwei
 */
public enum EnumMessageErrorCode {
    
    SUCCESS("0000", "�ɹ�"),
    ERROR("9999", "����������"),
    
    
    MESSAGE_ERROR("4100", "���Ĵ���"),
    
    HEADER_LEN_ERROR("4101", "����ͷ���ȴ���"),
    BODY_LEN_ERROR("4102", "�����峤�ȴ���"),
    TRANS_CODE_ERROR("4103", "���״������"), 
    PARAMS_ERROR("4104", "��������"),
    
    
    
    ;

    private EnumMessageErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static boolean isSuccess(String errorCode) {
        return SUCCESS.getCode().equals(errorCode);
    }

    public static boolean isSysError(String v) {
        return ERROR.getCode().equals(v);
    }

}

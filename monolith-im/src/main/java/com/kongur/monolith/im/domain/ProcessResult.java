package com.kongur.monolith.im.domain;

import com.kongur.monolith.common.result.Result;

/**
 * ��Ϣ������
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public class ProcessResult extends Result {

    /**
     * 
     */
    private static final long serialVersionUID = 4383547911770751385L;

    
    /**
     * ��Ϣ�����ص�����
     */
    private String            data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}

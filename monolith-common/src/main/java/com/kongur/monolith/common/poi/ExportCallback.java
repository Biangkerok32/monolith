package com.kongur.monolith.common.poi;

import org.apache.poi.hssf.usermodel.HSSFRow;


/**
 * �������ݻص��ӿ�
 * 
 * @author zhengwei
 * @param <T>
 */
public interface ExportCallback<T> {

    /**
     * ���һ������
     * 
     * @param row
     * @param t
     */
    void fillRowData(HSSFRow row, T t);
}

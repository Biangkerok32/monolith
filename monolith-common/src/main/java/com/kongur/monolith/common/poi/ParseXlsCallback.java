package com.kongur.monolith.common.poi;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.kongur.monolith.common.result.Result;

/**
 * ����xls�ص��ӿ�
 * 
 * @author zhengwei
 *
 * @param <E>
 */
public interface ParseXlsCallback<E> extends ParseCallback<E> {
    
    /**
     * ������ݶ���
     * 
     * @param obj
     * @param value
     * @param cellNum ��0��ʼ
     * @param result TODO
     */
    void fillObj(E obj, HSSFCell cell, String value, int cellNum, Result<List<E>> result);

}

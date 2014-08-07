package com.kongur.monolith.common.poi;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.kongur.monolith.common.result.Result;

/**
 * ����xlsx�ص��ӿ�
 * 
 * @author zhengwei
 * @param <E>
 */
public interface ParseXlsxCallback<E> extends ParseCallback<E> {

    /**
     * ������ݶ���
     * 
     * @param obj
     * @param value
     * @param cellNum ��0��ʼ
     */
    void fillObj(E obj, XSSFCell cell, String value, int cellNum, Result<List<E>> result);
}

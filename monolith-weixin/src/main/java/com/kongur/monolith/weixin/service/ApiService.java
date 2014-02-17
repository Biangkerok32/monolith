package com.kongur.monolith.weixin.service;

import java.util.Map;

import com.kongur.monolith.weixin.domain.ServiceResult;

/**
 * ƽ̨API����
 * 
 * @author zhengwei
 * @date 2014-2-17
 */
public interface ApiService {

    /**
     * ����get����
     * 
     * @param apiUrl ���� api URL
     * @return
     */
    ServiceResult<String> executeGet(String apiUrl) throws ExecuteException;

    /**
     * ����get����
     * 
     * @param apiUrl ���� api URL
     * @param getParams get�������
     * @return
     */
    ServiceResult<String> executeGet(String apiUrl, Map<String, String> getParams) throws ExecuteException;

    /**
     * ����post����
     * 
     * @param apiUrl ���� api URL
     * @param postParams post����
     * @return
     */
    ServiceResult<String> executePost(String apiUrl, String postParams) throws ExecuteException;
}

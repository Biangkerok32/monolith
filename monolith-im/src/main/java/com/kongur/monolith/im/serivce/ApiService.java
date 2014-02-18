package com.kongur.monolith.im.serivce;

import java.util.Map;

import net.sf.json.JSONObject;

import com.kongur.monolith.im.domain.ServiceResult;

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
    ServiceResult<String> executeGet(String apiUrl) throws ApiException;

    /**
     * ����get����
     * 
     * @param apiUrl ���� api URL
     * @param getParams get�������
     * @return
     */
    ServiceResult<String> executeGet(String apiUrl, Map<String, String> getParams) throws ApiException;

    /**
     * ����post����
     * 
     * @param apiUrl ���� api URL
     * @param postParams post����
     * @return
     */
    ServiceResult<String> executePost(String apiUrl, String postParams) throws ApiException;

    /**
     * ����get���󣬷��ؽ��ΪJSONObject
     * 
     * @param apiUrl
     * @return
     * @throws ApiException
     */
    ServiceResult<JSONObject> doGet(String apiUrl) throws ApiException;

    /**
     * ����get���󣬷��ؽ��ΪJSONObject
     * 
     * @param apiUrl ���� api URL
     * @param getParams get�������
     * @return
     */
    ServiceResult<JSONObject> doGet(String apiUrl, Map<String, String> getParams) throws ApiException;

    /**
     * ����post���󣬷��ؽ��ΪJSONObject
     * 
     * @param apiUrl ���� api URL
     * @param postParams post����
     * @return
     */
    ServiceResult<JSONObject> doPost(String apiUrl, String postParams) throws ApiException;

}

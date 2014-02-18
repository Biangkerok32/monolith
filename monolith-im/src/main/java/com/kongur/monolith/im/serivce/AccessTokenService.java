package com.kongur.monolith.im.serivce;

import com.kongur.monolith.im.domain.ServiceResult;

/**
 * AccessToken�������
 * 
 * @author zhengwei
 * @date 2014-2-14
 */
public interface AccessTokenService {

    /**
     * ˢ�� access token
     * 
     * @return
     */
    ServiceResult<String> refresh();

    /**
     * ��ȡAccessToken
     * 
     * @return
     */
    String getAccessToken();

}

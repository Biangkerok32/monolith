package com.kongur.monolith.weixin.service;

import java.util.List;

import com.kongur.monolith.weixin.domain.ServiceResult;
import com.kongur.monolith.weixin.domain.User;



/**
 *�û��������
 *
 *@author zhengwei
 *
 *@date 2014-2-17	
 *
 */
public interface UserManagerService {
    
    /**
     * ��ȡ��ע��
     * 
     * @return
     */
    ServiceResult<List<User>> getUsers();

}


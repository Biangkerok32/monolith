package com.kongur.monolith.im.serivce;

import java.util.List;

import com.kongur.monolith.im.domain.ServiceResult;
import com.kongur.monolith.im.domain.User;



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


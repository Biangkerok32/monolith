package com.runssnail.monolith.dal.router;

import com.alibaba.cobar.client.router.RoutingException;
import com.runssnail.monolith.dal.router.support.RoutingResult;

/**
 * ����Դ�ͱ���·��
 * 
 * @author zhengwei
 * @param <T>
 */
public interface Router<T> {

    /**
     * �ֿ�ֱ�
     * 
     * @param routingFact
     * @return
     * @throws RoutingException
     */
    RoutingResult doRoute(T routingFact) throws RoutingException;

}
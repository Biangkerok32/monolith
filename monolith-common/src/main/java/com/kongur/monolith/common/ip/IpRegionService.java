package com.kongur.monolith.common.ip;

import com.kongur.monolith.common.result.Result;

/**
 * ip����λ����
 * 
 * @author zhengwei
 */
public interface IpRegionService {

    /**
     * ����ip��ȡλ����Ϣ
     * 
     * @param ip
     * @return
     */
    Result<IpRegionDO> getIpRegion(String ip);

}

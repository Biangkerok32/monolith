package com.runssnail.monolith.common.ip;

import com.runssnail.monolith.common.result.Result;

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

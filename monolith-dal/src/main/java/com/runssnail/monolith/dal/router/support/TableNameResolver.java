package com.runssnail.monolith.dal.router.support;

/**
 * �����ֱ���
 * 
 * @author zhengwei
 *
 */
public interface TableNameResolver<F> {
    
    String resolveTableSuffix(F routingFact);
    
}

package com.kongur.monolith.dal.router.support;

/**
 * �����ֱ���
 * 
 * @author zhengwei
 *
 */
public interface TableNameResolver<F> {
    
    String resolveTableSuffix(F routingFact);
    
}

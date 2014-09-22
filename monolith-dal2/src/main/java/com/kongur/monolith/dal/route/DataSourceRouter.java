package com.kongur.monolith.dal.route;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * ����Դ·����
 * 
 * @author zhengwei
 */
public interface DataSourceRouter {

    /**
     * ����MappedStatement �� paramObjȷ���ĸ�DataSource
     * 
     * @param ms sql������Ϣ
     * @param paramObj ����
     * @return
     */
    DataSource routeDataSource(MappedStatement ms, Object paramObj);

    /**
     * ����statementId �� paramObjȷ���ĸ�DataSource
     * 
     * @param statementId sql mapper�ļ����sql��id
     * @param paramObj ����
     * @return
     */
    DataSource routeDataSource(String statementId, Object paramObj);

}

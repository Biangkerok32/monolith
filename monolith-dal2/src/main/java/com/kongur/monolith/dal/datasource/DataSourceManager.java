package com.kongur.monolith.dal.datasource;

import javax.sql.DataSource;

/**
 * ����Դ����
 * 
 * @author zhengwei
 *
 */
public interface DataSourceManager {

    /**
     * ��ȡ����Դ
     * 
     * @param dataSourceId ����Դid
     * @return
     */
    DataSource getDataSource(String dataSourceId);
    
}

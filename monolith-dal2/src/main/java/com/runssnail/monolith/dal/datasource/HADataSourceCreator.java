package com.runssnail.monolith.dal.datasource;

import javax.sql.DataSource;

/**
 * �ȱ�����Դ������
 * 
 * @author zhengwei
 *
 */
public interface HADataSourceCreator {

    DataSource createHADataSource(DataSourceDescriptor descriptor);

}

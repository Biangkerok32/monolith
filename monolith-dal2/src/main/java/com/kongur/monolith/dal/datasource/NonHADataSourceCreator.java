package com.kongur.monolith.dal.datasource;

import javax.sql.DataSource;

/**
 * Ĭ��ʵ��
 * 
 * @author zhengwei
 *
 */
public class NonHADataSourceCreator implements HADataSourceCreator {

    @Override
    public DataSource createHADataSource(DataSourceDescriptor descriptor) {
        return descriptor.getMasterDataSource();
    }

}

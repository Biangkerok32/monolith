package com.kongur.monolith.dal.datasources;

import com.alibaba.cobar.client.datasources.CobarDataSourceDescriptor;

/**
 * @author zhengwei
 */
public class KongurDataSourceDescriptor extends CobarDataSourceDescriptor {

    /**
     * �Ƿ���Ĭ�ϵ�����Դ
     */
    private boolean defaultDataSource;

    public boolean isDefaultDataSource() {
        return defaultDataSource;
    }

    public boolean getDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(boolean defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }
}

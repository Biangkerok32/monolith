package com.kongur.monolith.dal.datasources;

import com.alibaba.cobar.client.datasources.CobarDataSourceDescriptor;

/**
 * ����Դ����
 * 
 * @author zhengwei
 */
public class MonoDataSourceDescriptor extends CobarDataSourceDescriptor {

    /**
     * �Ƿ���Ĭ�ϵ�����Դ
     */
    private boolean defaultDataSource = false;

    /**
     * ̽��sql��̽������Դ�Ƿ����
     */
    private String  detectingSql      = "select 1 from dual";

    public String getDetectingSql() {
        return detectingSql;
    }

    public void setDetectingSql(String detectingSql) {
        this.detectingSql = detectingSql;
    }

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

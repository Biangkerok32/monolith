package com.runssnail.monolith.dal.datasource;

import javax.sql.DataSource;

/**
 * ����Դ����
 * 
 * @author zhengwei
 */
public class DataSourceDescriptor {

    /**
     * ����Դid
     */
    private String     identity;

    /**
     * ������Դ
     */
    private DataSource masterDataSource;

    /**
     * ̽���õ�������Դ����
     */
    private DataSource masterDetectorDataSource;

    /**
     * ����
     */
    private DataSource slaveDataSource;

    /**
     * ̽���õı���Դ����
     */
    private DataSource slaveDetectorDataSource;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    /**
     * ����
     * 
     * @return
     */
    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public DataSource getMasterDetectorDataSource() {
        return masterDetectorDataSource;
    }

    public void setMasterDetectorDataSource(DataSource masterDetectorDataSource) {
        this.masterDetectorDataSource = masterDetectorDataSource;
    }

    public DataSource getSlaveDataSource() {
        return slaveDataSource;
    }

    public void setSlaveDataSource(DataSource slaveDataSource) {
        this.slaveDataSource = slaveDataSource;
    }

    public DataSource getSlaveDetectorDataSource() {
        return slaveDetectorDataSource;
    }

    public void setSlaveDetectorDataSource(DataSource slaveDetectorDataSource) {
        this.slaveDetectorDataSource = slaveDetectorDataSource;
    }

}

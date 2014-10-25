package com.runssnail.monolith.dal.datasources;

import java.util.List;
import java.util.SortedMap;

import javax.sql.DataSource;

import com.alibaba.cobar.client.datasources.ICobarDataSourceService;

/**
 * ����Դ�������
 * 
 * @author zhengwei
 */
public interface MonoDataSourceService extends ICobarDataSourceService {

    /**
     * Ĭ�ϵ�����Դ
     * using MonoSqlMapClientTemplate's getDataSource() methed to fetch the default dataSource
     * @return
     */
    // public DataSource getDefaultDataSource();

    /**
     * Ĭ�ϵ�����Դ����
     * 
     * @return
     */
    // public MonoDataSourceDescriptor getDefaultDataSourceDescriptor();

    /**
     * �Ƿ��������Դ
     * 
     * @param dataSourceId ����ԴID
     * @return
     */
    boolean contains(String dataSourceId);

    /**
     * ��������Դ
     * 
     * @param dataSourceIdList ����ԴID�б�
     * @return
     */
    SortedMap<String, DataSource> getDataSourses(List<String> dataSourceIdList, boolean sort);

    /**
     * ��������ԴID��ȡ
     * 
     * @param dataSourceId
     * @return
     */
    DataSource getDataSource(String dataSourceId);
}

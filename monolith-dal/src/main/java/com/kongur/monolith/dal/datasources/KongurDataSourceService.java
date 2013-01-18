package com.kongur.monolith.dal.datasources;

import java.util.List;
import java.util.SortedMap;

import javax.sql.DataSource;

import com.alibaba.cobar.client.datasources.CobarDataSourceDescriptor;
import com.alibaba.cobar.client.datasources.ICobarDataSourceService;

/**
 * ����Դ�������
 * 
 * @author zhengwei
 */
public interface KongurDataSourceService extends ICobarDataSourceService {

    /**
     * Ĭ�ϵ�����Դ
     * 
     * @return
     */
    public DataSource getDefaultDataSource();

    /**
     * Ĭ�ϵ�����Դ����
     * 
     * @return
     */
    public CobarDataSourceDescriptor getDefaultDataSourceDescriptor();

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

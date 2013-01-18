package com.kongur.monolith.dal.datasources;

import java.util.List;
import java.util.SortedMap;

import javax.sql.DataSource;

import com.alibaba.cobar.client.datasources.CobarDataSourceDescriptor;
import com.alibaba.cobar.client.datasources.ICobarDataSourceService;

/**
 * 数据源管理服务
 * 
 * @author zhengwei
 */
public interface KongurDataSourceService extends ICobarDataSourceService {

    /**
     * 默认的数据源
     * 
     * @return
     */
    public DataSource getDefaultDataSource();

    /**
     * 默认的数据源配置
     * 
     * @return
     */
    public CobarDataSourceDescriptor getDefaultDataSourceDescriptor();

    /**
     * 是否存在数据源
     * 
     * @param dataSourceId 数据源ID
     * @return
     */
    boolean contains(String dataSourceId);

    /**
     * 返回数据源
     * 
     * @param dataSourceIdList 数据源ID列表
     * @return
     */
    SortedMap<String, DataSource> getDataSourses(List<String> dataSourceIdList, boolean sort);
    
    /**
     * 根据数据源ID获取
     * 
     * @param dataSourceId
     * @return
     */
    DataSource getDataSource(String dataSourceId);
}

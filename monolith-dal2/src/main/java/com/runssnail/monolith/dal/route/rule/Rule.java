package com.runssnail.monolith.dal.route.rule;

import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * ·�ɹ���
 * 
 * @author zhengwei
 */
public interface Rule {

    /**
     * �Ƿ�ƥ��
     * 
     * @param ms sql����
     * @param paramObj ִ��sqlʱ�Ĳ���
     * @return
     */
    boolean isMatch(MappedStatement ms, Object paramObj);
    
    /**
     * �Ƿ�ƥ��
     * 
     * @param statementId mybatis�����ļ���sql����id
     * @param paramObj ����
     * @return
     */
    boolean isMatch(String statementId, Object paramObj);

    /**
     * ����Դid
     * 
     * @return
     */
    String getDataSourceId();
    
    public Map<String, Object> getFunctions();

    public void setFunctions(Map<String, Object> functions);

}

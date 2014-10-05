package com.runssnail.monolith.dal.route.rule;

import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * ������sql id�Ĺ���
 * 
 * @author zhengwei
 *
 */
public class SqlIdRule extends AbstractRule {

    /**
     * 
     */
    private static final long serialVersionUID = 488134699456622226L;

    /**
     * mybaits mapper�ļ������õ�sql id
     */
    private String            sqlId;

    public SqlIdRule(String sqlId, String expression, String dataSourceId, Map<String, Object> functions) {
        super(expression, dataSourceId, functions);
        this.sqlId = sqlId;
    }

    @Override
    public boolean isMatch(MappedStatement ms, Object paramObj) {
        String statementId = ms.getId();
        return this.isMatch(statementId, paramObj);
    }

    @Override
    public boolean isMatch(String statementId, Object paramObj) {
        return statementId.equals(sqlId) && executeExpression(paramObj);
    }

}

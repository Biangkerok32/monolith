package com.runssnail.monolith.dal.route.rule;

import com.runssnail.monolith.common.DomainBase;

/**
 * �����壬����xml����
 * 
 * @author zhengwei
 */
public class DefaultRule extends DomainBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1426502807328051931L;

    /**
     * mybaits mapper�ļ������õ�sql id
     */
    private String            sqlId;

    /**
     * �����ռ�
     */
    private String            namespace;

    /**
     * ���ʽ
     */
    private String            expression;

    /**
     * ����Դid
     */
    private String            dataSourceId;

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

}

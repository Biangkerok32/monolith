package com.kongur.monolith.dal.router.rules.ibatis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.alibaba.cobar.client.router.support.IBatisRoutingFact;

/**
 * @author zhengwei
 */
public class KongurIBatisSqlActionRule extends AbstractKongurIBatisOrientedRule {

    public KongurIBatisSqlActionRule(String pattern, String action) {
        super(pattern, action);
    }

    public boolean isDefinedAt(IBatisRoutingFact routeFact) {
        Validate.notNull(routeFact);
        return StringUtils.equals(getTypePattern(), routeFact.getAction());
    }

    @Override
    public String toString() {
        return "KongurIBatisSqlActionRule [getAction()=" + getAction() + ", getTypePattern()=" + getTypePattern() + "]";
    }
}

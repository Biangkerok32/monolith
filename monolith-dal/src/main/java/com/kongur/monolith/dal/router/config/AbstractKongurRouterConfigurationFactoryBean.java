package com.kongur.monolith.dal.router.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

import com.alibaba.cobar.client.router.CobarClientInternalRouter;
import com.alibaba.cobar.client.router.rules.IRoutingRule;
import com.alibaba.cobar.client.router.support.IBatisRoutingFact;
import com.kongur.monolith.dal.router.DefaultRouter;

/**
 * 初始化KongurRouter
 * 
 * @author zhengwei
 */
public abstract class AbstractKongurRouterConfigurationFactoryBean implements FactoryBean, InitializingBean {

    private DefaultRouter       router;

    private boolean             enableCache;
    private int                 cacheSize;

    private Resource            configLocation;
    private Resource[]          configLocations;

    private Map<String, Object> functionsMap = new HashMap<String, Object>();

    /**
     * 默认的数据源ID
     */
    private String              defaultDataSourseId;

    public Object getObject() throws Exception {
        return this.router;
    }

    @SuppressWarnings("unchecked")
    public Class getObjectType() {
        return CobarClientInternalRouter.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        if (enableCache) {
            if (cacheSize <= 0) {
                setCacheSize(10000);
            }
        }
        this.router = new DefaultRouter(enableCache, cacheSize);

        final Set<IRoutingRule<IBatisRoutingFact, List<String>>> sqlActionShardingRules = new HashSet<IRoutingRule<IBatisRoutingFact, List<String>>>();
        final Set<IRoutingRule<IBatisRoutingFact, List<String>>> sqlActionRules = new HashSet<IRoutingRule<IBatisRoutingFact, List<String>>>();
        final Set<IRoutingRule<IBatisRoutingFact, List<String>>> namespaceShardingRules = new HashSet<IRoutingRule<IBatisRoutingFact, List<String>>>();
        final Set<IRoutingRule<IBatisRoutingFact, List<String>>> namespaceRules = new HashSet<IRoutingRule<IBatisRoutingFact, List<String>>>();

        if (getConfigLocation() != null) {
            assembleRulesForRouter(this.router, getConfigLocation(), sqlActionShardingRules, sqlActionRules,
                                   namespaceShardingRules, namespaceRules);
        }

        if (!ObjectUtils.isEmpty(getConfigLocations())) {
            for (Resource res : getConfigLocations()) {
                assembleRulesForRouter(this.router, res, sqlActionShardingRules, sqlActionRules,
                                       namespaceShardingRules, namespaceRules);
            }
        }

        List<Set<IRoutingRule<IBatisRoutingFact, List<String>>>> ruleSequences = new ArrayList<Set<IRoutingRule<IBatisRoutingFact, List<String>>>>() {

            private static final long serialVersionUID = 1493353938640646578L;
            {
                add(sqlActionShardingRules);
                add(sqlActionRules);
                add(namespaceShardingRules);
                add(namespaceRules);
            }
        };

        router.setRuleSequences(ruleSequences);
    }

    /**
     * Subclass just needs to read in rule configurations and assemble the router with the rules read from
     * configurations.
     * 
     * @param router
     * @param namespaceRules
     * @param namespaceShardingRules
     * @param sqlActionRules
     * @param sqlActionShardingRules
     */
    protected abstract void assembleRulesForRouter(DefaultRouter router,
                                                   Resource configLocation,
                                                   Set<IRoutingRule<IBatisRoutingFact, List<String>>> sqlActionShardingRules,
                                                   Set<IRoutingRule<IBatisRoutingFact, List<String>>> sqlActionRules,
                                                   Set<IRoutingRule<IBatisRoutingFact, List<String>>> namespaceShardingRules,
                                                   Set<IRoutingRule<IBatisRoutingFact, List<String>>> namespaceRules)
                                                                                                                     throws IOException;

    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    public Resource getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocations(Resource[] configLocations) {
        this.configLocations = configLocations;
    }

    public Resource[] getConfigLocations() {
        return configLocations;
    }

    public void setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
    }

    public boolean isEnableCache() {
        return enableCache;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setFunctionsMap(Map<String, Object> functionMaps) {
        if (functionMaps == null) {
            return;
        }
        this.functionsMap = functionMaps;
    }

    public Map<String, Object> getFunctionsMap() {
        return functionsMap;
    }

    public String getDefaultDataSourseId() {
        return defaultDataSourseId;
    }

    public void setDefaultDataSourseId(String defaultDataSourseId) {
        this.defaultDataSourseId = defaultDataSourseId;
    }

    public DefaultRouter getRouter() {
        return router;
    }

    public void setRouter(DefaultRouter router) {
        this.router = router;
    }

}

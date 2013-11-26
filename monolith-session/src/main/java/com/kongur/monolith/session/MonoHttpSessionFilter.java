package com.kongur.monolith.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import com.kongur.monolith.session.attibute.AttributesConfigManager;
import com.kongur.monolith.session.attibute.DefaultAttributesConfigManager;
import com.kongur.monolith.session.store.CookieSessionAttributeStore;
import com.kongur.monolith.session.store.SessionAttributeStore;

/**
 * monolith session filter
 * 
 * @author wade.zheng
 * @date��2011-6-15
 */

public class MonoHttpSessionFilter implements Filter {

    /**
     * session ���Թ�����
     */
    private AttributesConfigManager attributesConfigManager;

    private static final String     ATTRIBUTES_CONFIG_MANAGER_CLASS = "attributesConfigManager";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        initAttributesConfigManager(filterConfig);
    }

    /**
     * ��ʼ��AttributesConfigManager
     * 
     * @param filterConfig
     * @throws ServletException
     */
    private void initAttributesConfigManager(FilterConfig filterConfig) throws ServletException {

        if (filterConfig != null) {
            String attributesConfigManagerClazz = filterConfig.getInitParameter(ATTRIBUTES_CONFIG_MANAGER_CLASS);

            if (!StringUtils.isBlank(attributesConfigManagerClazz)) {
                try {
                    Class clazz = ClassUtils.getClass(attributesConfigManagerClazz);
                    attributesConfigManager = (AttributesConfigManager) clazz.newInstance();
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }

        }

        if (attributesConfigManager == null) {
            DefaultAttributesConfigManager defaultAttributesConfigManager = new DefaultAttributesConfigManager();
            attributesConfigManager = defaultAttributesConfigManager;
        }

        attributesConfigManager.init();

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        // ���������filter��������exception��
        // ��weblogic�У�servlet forward��jspʱ��jsp�Ի���ô�filter����jsp�׳����쳣�ͻᱻ��filter����
        if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse)
            || (request.getAttribute(getClass().getName()) != null)) {
            chain.doFilter(request, response);
            return;
        }

        // ��ֹ����.
        request.setAttribute(getClass().getName(), Boolean.TRUE);

        HttpServletRequest httpReq = (HttpServletRequest) request;
        MonoHttpServletRequest monoRequest = new MonoHttpServletRequest(httpReq);
        MonoHttpServletResponse monoResponse = new MonoHttpServletResponse((HttpServletResponse) response);

        MonoHttpSession session = createMonoHttpSession(monoRequest, monoResponse, httpReq.getSession());
        monoRequest.setSession(session);
        monoResponse.setSession(session);

        try {
            chain.doFilter(monoRequest, monoResponse);
            if (session != null) {
                session.commit(); // ���޸Ĺ���cookie��ӵ�response��
            }
        } finally {

            // ����������д��response����
            monoResponse.commitBuffer();
        }

    }

    /**
     * ����session
     * 
     * @param monoRequest
     * @param monoResponse
     * @param httpSession
     * @return
     */
    private MonoHttpSession createMonoHttpSession(MonoHttpServletRequest monoRequest,
                                                  MonoHttpServletResponse monoResponse, HttpSession httpSession) {
        List<SessionAttributeStore> stores = getStores();
        MonoHttpSession session = new MonoHttpSession(monoRequest, monoResponse, httpSession, stores,
                                                      attributesConfigManager);
        session.init();
        return session;
    }

    /**
     * ��ȡSessionStores
     * 
     * @return
     */
    private List<SessionAttributeStore> getStores() {
        List<SessionAttributeStore> stores = new ArrayList<SessionAttributeStore>();
        CookieSessionAttributeStore cookieStore = new CookieSessionAttributeStore();
        stores.add(cookieStore);
        return stores;
    }

    @Override
    public void destroy() {
        this.attributesConfigManager.destroy();
    }

    public void setAttributesConfigManager(AttributesConfigManager attributesConfigManager) {
        this.attributesConfigManager = attributesConfigManager;
    }

}

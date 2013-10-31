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

import com.kongur.monolith.session.attibute.AttributesConfigManager;
import com.kongur.monolith.session.attibute.DefaultAttributesConfigManager;

/**
 * monolith session filter
 * 
 * @author wade.zheng
 * @date��2011-6-15
 */

public class MonoHttpSessionFilter implements Filter {

    private FilterConfig            filterConfig;

    /**
     * session ���Թ�����
     */
    private AttributesConfigManager attributesConfigManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;

        initAttributesConfigManager();
    }

    /**
     * ��ʼ��AttributesConfigManager
     */
    private void initAttributesConfigManager() {

        if (attributesConfigManager == null) {
            DefaultAttributesConfigManager defaultAttributesConfigManager = new DefaultAttributesConfigManager();
            defaultAttributesConfigManager.init();

            attributesConfigManager = defaultAttributesConfigManager;
        }

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

        MonoHttpServletRequest monoRequest = new MonoHttpServletRequest((HttpServletRequest) request);
        MonoHttpServletResponse monoResponse = new MonoHttpServletResponse((HttpServletResponse) response);
        MonoHttpSession session = createMonoHttpSession(monoRequest, monoResponse);
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
     * @return
     */
    private MonoHttpSession createMonoHttpSession(MonoHttpServletRequest monoRequest,
                                                  MonoHttpServletResponse monoResponse) {
        List<SessionAttributeStore> stores = getStores();
        MonoHttpSession session = new MonoHttpSession(monoRequest, monoResponse, filterConfig.getServletContext(),
                                                      stores, attributesConfigManager);
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

    }

    public void setAttributesConfigManager(AttributesConfigManager attributesConfigManager) {
        this.attributesConfigManager = attributesConfigManager;
    }

}

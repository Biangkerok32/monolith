package com.runssnail.monolith.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * ֧���ʼ�ģ�建��
 * 
 * @author zhengwei
 */
public abstract class AbstractCachingMailTemplateResolver implements MailTemplateResolver {

    private Map<Object, MailTemplate> mailTemplateCache = new HashMap<Object, MailTemplate>();

    @Override
    public MailTemplate resolveTemplate(String template, Locale locale) {

        if (StringUtils.isBlank(template)) {
            throw new RuntimeException("template can not be blank!");
        }

        Object cacheKey = getCacheKey(template, locale);

        synchronized (this.mailTemplateCache) {
            MailTemplate mailTemplate = mailTemplateCache.get(cacheKey);
            if (mailTemplate == null) {
                mailTemplate = createMailTemplate(template, locale);
                mailTemplateCache.put(cacheKey, mailTemplate);
            }

            return mailTemplate;
        }

    }

    protected Object getCacheKey(String template, Locale locale) {
        if (locale == null) {
            return template;
        }

        return template + "_" + locale;
    }

    /**
     * �����ʼ�ģ�����, ����ģ���ļ����ƺ�����������
     * 
     * @param template
     * @param locale
     * @return
     */
    protected abstract MailTemplate createMailTemplate(String template, Locale locale);

}

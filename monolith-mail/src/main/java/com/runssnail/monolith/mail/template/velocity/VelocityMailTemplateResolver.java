package com.runssnail.monolith.mail.template.velocity;

import java.util.Locale;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

import com.runssnail.monolith.mail.template.AbstractCachingMailTemplateResolver;
import com.runssnail.monolith.mail.template.MailTemplate;


/**
 * ����velocity�ʼ�ģ��
 * 
 * @author zhengwei
 */
public class VelocityMailTemplateResolver extends AbstractCachingMailTemplateResolver {

    private VelocityEngine      velocityEngine;

    private static final String TEMPLATE_SUFFIX = ".vm";

    @Override
    protected MailTemplate createMailTemplate(String templateName, Locale locale) {

        // ����ģ���ļ����ƺ�����������ģ��
        String finalName = findTemplateName(templateName, locale);

        Template template = velocityEngine.getTemplate(finalName);

        return new VelocityMailTemplate(template);
    }

    /**
     * ����ģ���ļ����ƺ�����������ģ��, test->test.vm or test_en_US.vm or test_zh_CN.vm
     * 
     * @param templateName ģ������
     * @param locale ����
     * @return
     */
    protected String findTemplateName(String templateName, Locale locale) {
        String retName = templateName;
        if (!retName.endsWith(TEMPLATE_SUFFIX)) {
            retName += retName + TEMPLATE_SUFFIX;
        }

        if (locale == null) {
            return retName;
        }

        StringBuilder sb = new StringBuilder(retName);
        String localeString = "_" + locale.getLanguage() + "_" + locale.getCountry();
        sb.insert(retName.length() - TEMPLATE_SUFFIX.length(), localeString);
        localeString = sb.toString();

        // �жϹ��ʻ�ģ���ļ��Ƿ����, ������ʹ�ù��ʻ�ģ�壬����ʹ��Ĭ��ģ��
        if (velocityEngine.resourceExists(localeString)) {
            return localeString;
        }

        return retName;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

}

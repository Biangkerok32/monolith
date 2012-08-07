package com.kongur.monolith.mail;

import java.util.Locale;


/**
 * �ʼ�ģ����ҹ��ߣ� ����locale�����Ҳ�ͬ���԰汾���ʼ�ģ��
 * 
 * @author zhengwei
 *
 */
public interface MailTemplateResolver {
    
    /**
     * �����ʼ�ģ��
     * 
     * @param template ģ������ test.vm
     * @param locale 
     * @return
     */
    MailTemplate resolveTemplate(String template, Locale locale);
}

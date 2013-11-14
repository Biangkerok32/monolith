package com.kongur.monolith.mail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * �ʼ����ݶ���
 * 
 * @author zhengwei
 */
public class MailDO implements Serializable {

    /**
     * 
     */
    private static final long   serialVersionUID = -4306347729988226975L;

    /**
     * ������
     */
    private String              from;

    /**
     * �ռ���
     */
    private String[]            to;

    /**
     * ��
     */
    private String              replyTo;

    /**
     * ����
     */
    private String              subject;

    /**
     * �ʼ�ģ��
     */
    private String              template;

    /**
     * ����
     */
    private Map<String, Object> params;

    /**
     * �Ƿ�HTML����
     */
    private boolean             html;

    /**
     * ����, ��ͨ��template�����ɣ�����ⲿ������content, ��ô����ͨ��template����������
     */
    private String              content;

    private Locale              locale;

    public MailDO(String to, String subject, String template) {
        this(null, to, subject, template);
    }

    public MailDO(String from, String to, String subject, String template) {
        this.from = from;
        this.to = new String[] { to };
        this.subject = subject;
        this.template = template;
    }

    public MailDO(String from, String[] to, String subject, String template) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.template = template;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public void addParam(String key, Object val) {
        if (this.params == null) {
            params = new HashMap<String, Object>();
        }

        params.put(key, val);
    }

}

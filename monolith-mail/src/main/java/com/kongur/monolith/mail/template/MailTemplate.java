package com.kongur.monolith.mail.template;

import java.util.Map;

/**
 * �ʼ�ģ��
 * 
 * @author zhengwei
 *
 */
public interface MailTemplate {

    /**
     * ���ݲ�����������
     * 
     * @param model ����
     * @return
     */
    String render(Map<String, ?> model);

}

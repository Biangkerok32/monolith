package com.kongur.monolith.mail.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.kongur.monolith.mail.AbstractMailSendService;
import com.kongur.monolith.mail.MailDO;

/**
 * Ĭ�ϵ��ʼ�����ʵ��
 * 
 * @author zhengwei
 */
public class DefaultMailSendService extends AbstractMailSendService {

    private JavaMailSender javaMailSender;

    @Override
    protected void doSend(MailDO mail) throws Exception {

        javaMailSender.send(createMimeMessage(mail));

    }

    private MimeMessage createMimeMessage(MailDO mail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        if (mail.getFrom() != null) {
            helper.setFrom(mail.getFrom());
        }

        if (mail.getReplyTo() != null) {
            helper.setReplyTo(mail.getReplyTo());
        }

        helper.setTo(mail.getTo());

        if (mail.getSubject() != null) {
            helper.setSubject(mail.getSubject());
        }

        if (mail.getContent() != null) {
            if (mail.isHtml()) {
                helper.setText(mail.getContent(), true);
            } else {
                helper.setText(mail.getContent());
            }
        }

        return message;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

}

package com.runssnail.monolith.mail;

/**
 * �ʼ����ͷ���
 * 
 * @author zhengwei
 */
public interface MailSendService {

    /**
     * ͬ�����͵����ʼ�
     * 
     * @param mail
     */
    SendResult send(MailDO mail) throws SendMailException;

    /**
     * ͬ�����Ͷ���ʼ�
     * 
     * @param mails
     */
    SendResult send(MailDO[] mails) throws SendMailException;

    /**
     * �첽���͵����ʼ�
     * 
     * @param mail
     */
    SendResult asynSend(MailDO mail) throws SendMailException;

    /**
     * �첽���Ͷ���ʼ�
     * 
     * @param mails
     */
    SendResult asynSend(MailDO[] mails) throws SendMailException;

}

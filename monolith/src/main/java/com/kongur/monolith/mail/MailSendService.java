package com.kongur.monolith.mail;

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
    void synSend(MailDO mail) throws MailException;

    /**
     * ͬ�����Ͷ���ʼ�
     * 
     * @param mails
     */
    void synSend(MailDO[] mails) throws MailException;

    /**
     * �첽���͵����ʼ�
     * 
     * @param mail
     */
    void asynSend(MailDO mail) throws MailException;

    /**
     * �첽���Ͷ���ʼ�
     * 
     * @param mails
     */
    void asynSend(MailDO[] mails) throws MailException;

}

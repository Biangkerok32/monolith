package com.kongur.monolith.mail;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * @author zhengwei
 */
public abstract class AbstractMailSendService implements MailSendService {
    
    protected final Logger logger = Logger.getLogger(getClass());

    private static final Executor DEFAULT_EXECUTOR = Executors.newSingleThreadExecutor();

    private Executor              executor         = DEFAULT_EXECUTOR;

    /**
     * �����ʼ�ģ��
     */
    private MailTemplateResolver  mailTemplateResolver;

    @Override
    public void synSend(MailDO mail) throws MailException {

        try {

            renderContent(mail);

            doSend(mail);
        } catch (Exception e) {
            throw new MailException(e);
        }
    }

    /**
     * �����ʼ�����
     * 
     * @param mail
     */
    protected void renderContent(MailDO mail) {
        // �����ʼ�ģ��
        MailTemplate mailTemplate = mailTemplateResolver.resolveTemplate(mail.getTemplate(), mail.getLocale());

        String content = mailTemplate.render(mail.getParams());

        mail.setContent(content);
    }

    @Override
    public void synSend(final MailDO[] mails) throws MailException {
        for (MailDO mail : mails) {
            synSend(mail);
        }
    }

    @Override
    public void asynSend(final MailDO mail) throws MailException {

        renderContent(mail);
        
        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    doSend(mail);
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        });
    }

    @Override
    public void asynSend(MailDO[] mails) throws MailException {
        for (MailDO mail : mails) {
            asynSend(mail);
        }
    }

    /**
     * sub class to impl
     * 
     * @param mail
     */
    protected abstract void doSend(MailDO mail) throws Exception;

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public MailTemplateResolver getMailTemplateResolver() {
        return mailTemplateResolver;
    }

    public void setMailTemplateResolver(MailTemplateResolver mailTemplateResolver) {
        this.mailTemplateResolver = mailTemplateResolver;
    }

}

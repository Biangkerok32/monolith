package com.runssnail.monolith.mail.template.velocity;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.runssnail.monolith.mail.template.MailTemplate;


/**
 * ��velocity�ļ���Ϊ�ʼ�ģ��
 * 
 * @author zhengwei
 */
public class VelocityMailTemplate implements MailTemplate {

    private Template template;

    public VelocityMailTemplate() {

    }

    public VelocityMailTemplate(Template template) {
        this.template = template;
    }

    @Override
    public String render(Map<String, ?> model) {

        VelocityContext velocityContext = new VelocityContext(model);

        StringWriter writer = new StringWriter();

        template.merge(velocityContext, writer);

        return writer.toString();
    }

}

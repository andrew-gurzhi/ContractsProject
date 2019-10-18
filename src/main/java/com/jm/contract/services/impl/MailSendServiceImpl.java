package com.jm.contract.services.impl;

import com.jm.contract.models.Client;
import com.jm.contract.models.ContractDataForm;
import com.jm.contract.models.User;
import com.jm.contract.services.interfaces.ClientService;
import com.jm.contract.services.interfaces.MailSendService;
import com.jm.contract.services.interfaces.ProjectPropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@EnableAsync
//@PropertySources(value = {
//        @PropertySource("classpath:application.properties"),
//        @PropertySource(value = "file:./javamentortest.properties", encoding = "Cp1251"),
//        @PropertySource(value = "file:./monthly-report.properties", encoding = "Cp1251")
//})

//@PropertySource(value = "file:./monthly-report.properties", encoding = "Cp1251")

public class MailSendServiceImpl implements MailSendService {

    private static Logger logger = LoggerFactory.getLogger(MailSendServiceImpl.class);

    private final JavaMailSender javaMailSender;
    private final TemplateEngine htmlTemplateEngine;
    private final ClientService clientService;
//    private final MessageService messageService;
//    private final MailConfig mailConfig;
    private String emailLogin;
    private final Environment env;
    private final ProjectPropertiesService projectPropertiesService;


    @Autowired
    public MailSendServiceImpl(JavaMailSender javaMailSender,
                               @Qualifier("thymeleafTemplateEngine") TemplateEngine htmlTemplateEngine,
                               Environment environment,
                               @Lazy ClientService clientService,
//                               ClientHistoryService clientHistoryService,
//                               MessageService messageService,
//                               MailConfig mailConfig,
                               ProjectPropertiesService projectPropertiesService) {
        this.javaMailSender = javaMailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
        this.clientService = clientService;
//        this.clientHistoryService = clientHistoryService;
//        this.messageService = messageService;
        this.env = environment;
//        this.mailConfig = mailConfig;
        this.projectPropertiesService = projectPropertiesService;
        checkConfig(environment);
    }


    private void checkConfig(Environment environment) {
        try {
            this.emailLogin = environment.getRequiredProperty("spring.mail.username");
            String password = environment.getRequiredProperty("spring.mail.password");
            if (emailLogin.isEmpty() || password.isEmpty()) {
                throw new NoSuchFieldException();
            }
        } catch (IllegalStateException | NoSuchFieldException e) {
            logger.error("Mail configs have not initialized. Check application.properties file", e);
            System.exit(1);
        }
    }


    public void prepareAndSend(ContractDataForm clientData, String templateText, String body, String templateTheme,String docLink) {
//        String templateFile = "emailStringTemplate";
//        Optional<Client> client = clientService.getClientByID(clientId);
//        if (client.isPresent()) {
//            Optional<String> emailOptional = client.get().getEmail();
//            if (emailOptional.isPresent() && !emailOptional.get().isEmpty()) {
//                String recipient = client.get().getEmail().get();
//                String fullName = client.get().getName() + " " + client.get().getLastName();
//                Map<String, String> params = new HashMap<>();
//                if (client.get().getContractLinkData() != null) {
//                    String link = client.get().getContractLinkData().getContractLink();
//                    params.put("%contractLink%", link);
//                }
//                Optional<String> emailOptional = clientData.getEmail();
//                if (emailOptional.isPresent() && !emailOptional.get().isEmpty()) {
                    String recipient = clientData.getInputEmail();
                    String fullName = clientData.getInputFirstName() + " " + clientData.getInputLastName();
                    Map<String, String> params = new HashMap<>();
                        params.put("%contractLink%", docLink);

                    //TODO в конфиг
                params.put("%fullName%", fullName);
                params.put("%bodyText%", body);
                final Context ctx = new Context();
                templateText = templateText.replaceAll("\n", "");
                ctx.setVariable("templateText", templateText);
                final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                try {
                    final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    if (templateTheme == null) {
                        mimeMessageHelper.setSubject("Java Mentor");
                    } else {
                        mimeMessageHelper.setSubject(templateTheme);
                    }
                    mimeMessageHelper.setTo(recipient);
                    mimeMessageHelper.setFrom(emailLogin);
                    StringBuilder htmlContent = new StringBuilder();
//                    for (Map.Entry<String, String> entry : params.entrySet()) {
//                        htmlContent = new StringBuilder(templateText.replaceAll(entry.getKey(), entry.getValue()));
//                    }
                    htmlContent.append(templateText+" "+ docLink);
                    mimeMessageHelper.setText(htmlContent.toString(), true);
                    Pattern pattern = Pattern.compile("(?<=cid:)\\S*(?=\\|)");
                    //inline картинки присоединяются к тексту сообщения с помочью метода addInline(в какое место вставлять, что вставлять).
                    //Добавлять нужно в тег data-th-src="|cid:XXX|" где XXX - имя загружаемого файла
                    //Регулярка находит все нужные теги, а потом циклом добавляем туда нужные файлы.
                    Matcher matcher = pattern.matcher(templateText);
                    while (matcher.find()) {
                        String path = (matcher.group()).replaceAll("/", "\\" + File.separator);
                        File file = new File(path);
                        if (file.exists()) {
                            InputStreamSource inputStreamSource = new FileSystemResource(file);
                            mimeMessageHelper.addInline(matcher.group(), inputStreamSource, "image/jpeg");
                        } else {
                            logger.error("Can not send message! Template attachment file {} not found. Fix email template.", file.getCanonicalPath());
                            return;
                        }
                    }
                    javaMailSender.send(mimeMessage);
//                    if (principal != null) {
//                        Optional<Client> clientEmail = clientService.getClientByEmail(recipient);
//                        Optional<Message> message = messageService.addMessage(Message.Type.EMAIL, htmlContent.toString(), principal.getFullName());
//                        if (clientEmail.isPresent() && message.isPresent()) {
//                            clientHistoryService.createHistory(principal, clientEmail.get(), message.get()).ifPresent(client.get()::addHistory);
//                            clientService.updateClient(client.get());
//                        } else {
//                            logger.error("Can't send mail to {}", recipient);
//                        }
//                    }
                } catch (Exception e) {
                    logger.error("Can't send mail to {}", recipient, e);
                }

//         else {
//            logger.error("Can not send message! client id {} email not found or empty", client.get().getId());
    }

	@Override
	public void sendContractWithoutToken(String recipient, File file, String templateText) {
		MimeMessage message = javaMailSender.createMimeMessage();
        templateText = templateText.replaceAll("\n", "");
        final Context ctx = new Context();
        ctx.setVariable("templateText", templateText);

        try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true,"UTF-8");
			mimeMessageHelper.setTo(recipient);
			mimeMessageHelper.setFrom(emailLogin);
			mimeMessageHelper.setText("Hello, this is a contract",true);
			FileSystemResource fileSystemResource = new FileSystemResource(file);
			mimeMessageHelper.addAttachment(file.getName(),fileSystemResource);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

//    @Override
//    public void sendSimpleNotification(Long clientId, String templateText) {
//        prepareAndSend(clientId, templateText, "", null, null);
//    }

    @Async
    public void sendNotificationMessage(User userToNotify, String notificationMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(env.getProperty("messaging.mailing.set-subject-crm-notification"));
        message.setText(notificationMessage);
        message.setFrom(emailLogin);
        message.setTo(userToNotify.getEmail());
        javaMailSender.send(message);
    }


	@Override
    public void sendReportToJavaMentorEmail(String report) {
        User user = new User();
        String javaMentorEmail = env.getRequiredProperty("report.mail");
        user.setEmail(javaMentorEmail);
        sendNotificationMessage(user, report);
    }

}

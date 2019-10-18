package com.jm.contract.repository.interfaces;

import com.jm.contract.models.Client;
import com.jm.contract.models.User;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

public interface MailSendService {
    void validatorTestResult(String parseContent, Client client) throws MessagingException;

    void prepareAndSend(Long clientId, String templateText, String body, User principal, String templateTheme);

    /**
     * Send email notification to client without logging and additional body parameters.
     *
     * @param clientId     recipient client.
     * @param templateText email template text.
     */
    void sendSimpleNotification(Long clientId, String templateText);

    void sendNotificationMessage(User userToNotify, String notificationMessage);

    void sendReportToJavaMentorEmail(String report);

    void sendEmailInAllCases(Client client);

    void sendMessage(String subject, String text, String email);
}

package com.jm.contract.services.interfaces;

import com.jm.contract.models.ContractDataForm;
import com.jm.contract.models.User;

import java.io.File;

public interface MailSendService {
//    void validatorTestResult(String parseContent, Client client) throws MessagingException;

    void prepareAndSend(ContractDataForm clientData, String templateText, String body, String templateTheme, String docLink);

    /**
     * Send email notification to client without logging and additional body parameters.
     *
     * @param clientId     recipient client.
     * @param templateText email template text.
     */
//    void sendSimpleNotification(Long clientId, String templateText);

    void sendNotificationMessage(User userToNotify, String notificationMessage);

    void sendContractWithoutToken(String email, File file, String templateText);

    void sendReportToJavaMentorEmail(String report);

//    void sendEmailInAllCases(Client client);
//
//    void sendMessage(String subject, String text, String email);
}

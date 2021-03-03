package com.eteach.eteach.event.resetPasswordLink;

import com.eteach.eteach.mail.MailService;
import com.eteach.eteach.model.account.User;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class GenerateResetLinkEventListener implements ApplicationListener<GenerateResetLinkEvent> {


    private final MailService mailService;

    @Autowired
    public GenerateResetLinkEventListener(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * As soon as a forgot password link is clicked and a valid email id is entered,
     * Reset password link will be sent to respective mail via this event
     */
    @Override
    @Async
    public void onApplicationEvent(GenerateResetLinkEvent generateResetLinkEvent) {
        //sendResetLink(generateResetLinkEvent);
    }

    /*
     * Sends Reset Link to the mail address with a password reset link token
     */

    /*----------

    private void sendResetLink(GenerateResetLinkEvent event) {
        User user = passwordResetToken.getUser();
        String recipientAddress = user.getEmail();
        String emailConfirmationUrl = event.getRedirectUrl().queryParam("token", passwordResetToken.getToken())
                .toUriString();
        try {
            mailService.sendResetLink(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }
    ----------------*/


}

package com.todo.list.api.application.applicationservice.email;

import com.todo.list.api.application.dto.email.EmailDTO;
import com.todo.list.api.application.enums.StatusEmail;
import com.todo.list.api.domain.model.email.Email;
import com.todo.list.api.domain.model.email.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailApplicationServiceImpl implements EmailApplicationService {

    public final EmailRepository emailRepository;

    public JavaMailSender javaMailSender;

    @Autowired
    public EmailApplicationServiceImpl(final EmailRepository emailRepository, JavaMailSender javaMailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }

    @SuppressWarnings("finally")
	@Override
    public Email sendEmail(EmailDTO emailDTO) {
        emailDTO.updateSendDateEmail(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailDTO.getEmailFrom());
            message.setTo(emailDTO.getEmailTo());
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getText());
            javaMailSender.send(message);

            emailDTO.updateStatusEmail(StatusEmail.SENT);
        }
        catch(MailException e) {
            emailDTO.updateStatusEmail(StatusEmail.ERROR);
        }
        catch(Exception e) {
            emailDTO.updateStatusEmail(StatusEmail.ERROR);
        }
        finally {
            Email email = new Email(emailDTO.getId()
                    , emailDTO.getOwnerRef()
                    , emailDTO.getEmailFrom()
                    , emailDTO.getEmailTo()
                    , emailDTO.getSubject()
                    , emailDTO.getText()
                    , emailDTO.getSendDateEmail()
                    , emailDTO.getStatusEmail());

            return emailRepository.save(email);
        }
    }
}

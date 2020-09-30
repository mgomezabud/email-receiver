package com.zabud.email.receiver.infraestructure.service;

import java.util.Optional;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;
import com.zabud.email.receiver.domain.entity.billingmessage.DocumentMessage;
import com.zabud.email.receiver.domain.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import com.zabud.email.receiver.domain.service.DocumentMessageReader;
import com.zabud.email.receiver.domain.service.DocumentMessageReceiver;
import com.zabud.email.receiver.shared.event.Event;

@Slf4j
@Service
public class EmailDocumentMessageReceiver extends DocumentMessageReceiver<Message<?>>{

  @Autowired
  private DocumentMessageReader<MimeMessage> documentReader;
  
  @Autowired
  private EventPublisher eventPublisher;

  @Bean
  @ServiceActivator(inputChannel = "imapChannel")
  public MessageHandler processNewEmail() {
    return this::receiveMessage;
  }

  @Override
  public Optional<DocumentMessage> parseMessage(Message<?> message) {
    MimeMessage email = (MimeMessage) message.getPayload();
    return documentReader.read(email);
  }

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public void publishMessage(DocumentMessage documentMessage) {
    eventPublisher.publishEvent(new Event<>("REGISTRO_MENSAJE_DOCUMENTO", documentMessage));
  }
  
}

package com.zabud.email.receiver.domain.service;

import java.util.Optional;
import org.slf4j.Logger;
import com.zabud.email.receiver.domain.entity.billingmessage.DocumentMessage;
import com.zabud.email.receiver.shared.exception.MessageProcessorException;
import com.zabud.email.receiver.shared.exception.MessageReaderException;
import lombok.val;

public abstract class DocumentMessageReceiver<T> {

  public final void receiveMessage(T message) {
    val document = parseMessage(message);
    try {
      logMessage(document);
    } catch (Exception e) {
      throw new MessageReaderException(e);
    }
    try {
      publishMessage(document.get());
    } catch (Exception e) {
      throw new MessageProcessorException(e);
    }
  }
  
  public void logMessage(Optional<DocumentMessage> documentOptional) {
    documentOptional.ifPresent(document -> {
      getLogger().info(document.toString());
    });
  }
  
  public abstract Logger getLogger();

  public abstract Optional<DocumentMessage> parseMessage(T message);

  public abstract void publishMessage(DocumentMessage documentMessage);

}

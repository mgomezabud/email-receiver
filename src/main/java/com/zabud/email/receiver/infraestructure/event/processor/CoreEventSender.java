package com.zabud.email.receiver.infraestructure.event.processor;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.zabud.email.receiver.domain.entity.billingmessage.DocumentMessage;
import com.zabud.email.receiver.domain.event.EventProcessor;
import com.zabud.email.receiver.shared.event.Event;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CoreEventSender implements EventProcessor {

  private List<String> TYPE_EVENTS = Arrays.asList("REGISTRO_MENSAJE_DOCUMENTO"); 
  
  @Override
  public void process(Event<?> event) {
    DocumentMessage message = (DocumentMessage) event.getData();
    log.info("Enviar evento para el core "+ message.getDocument().getUbl().substring(0, 100)+"...");
  }

  @Override
  public boolean canProcessType(String eventType) {
    return TYPE_EVENTS.contains(eventType);
  }

}

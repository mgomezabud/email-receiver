package com.zabud.email.receiver.infraestructure.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import com.zabud.email.receiver.domain.event.EventPublisher;
import com.zabud.email.receiver.shared.event.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventPublisherImpl implements EventPublisher {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publishEvent(Event<?> event) {
    log.debug("Publish event; id: %, type: %",event.getId(), event.getType());
    this.applicationEventPublisher.publishEvent(new CustomEvent(event));
  }

}

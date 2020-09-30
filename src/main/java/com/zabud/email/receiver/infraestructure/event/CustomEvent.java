package com.zabud.email.receiver.infraestructure.event;

import org.springframework.context.ApplicationEvent;
import com.zabud.email.receiver.shared.event.Event;

public class CustomEvent extends ApplicationEvent {
  private static final long serialVersionUID = -7670593525206778917L;

  public CustomEvent(Event<?> event) {
    super(event);
  }

}

package com.zabud.email.receiver.domain.event;

import com.zabud.email.receiver.shared.event.Event;

public interface EventPublisher {
  
  public void publishEvent(Event<?> event);

}

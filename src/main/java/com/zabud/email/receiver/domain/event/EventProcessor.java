package com.zabud.email.receiver.domain.event;

import com.zabud.email.receiver.shared.event.Event;

public interface EventProcessor {

  public void process(Event<?> event);
  
  public boolean canProcessType(String eventType);
  
}

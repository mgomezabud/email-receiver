package com.zabud.email.receiver.infraestructure.event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.zabud.email.receiver.domain.event.EventProcessor;
import com.zabud.email.receiver.shared.event.Event;
import com.zabud.email.receiver.shared.exception.UncontrolledException;
import com.zabud.email.receiver.shared.exception.InvalidEventProcessorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventListener implements ApplicationListener<CustomEvent> {

  @Autowired
  private ApplicationContext context;

  @Override
  public void onApplicationEvent(CustomEvent customEvent) {
    log.debug("Received event: %", customEvent.toString());
    try {
      Event<?> event = (Event<?>) customEvent.getSource();
      List<EventProcessor> processors = getEventProcessors(event);
      processors.parallelStream().forEach(ep -> ep.process(event));
    } catch (Exception e) {
      throw new UncontrolledException(e);
    }
  }

  private List<EventProcessor> getEventProcessors(Event<?> event) {
    String[] names = context.getBeanNamesForType(EventProcessor.class);
    return Arrays.stream(names).map(this::loadProcessor)
        .filter(x -> x.canProcessType(event.getType())).collect(Collectors.toList());
  }

  private EventProcessor loadProcessor(String name) {
    Object processorObject = context.getBean(name);
    if (!(processorObject instanceof EventProcessor)) {
      throw new InvalidEventProcessorException(name);
    }
    return (EventProcessor) processorObject;
  }

}

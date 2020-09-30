package com.zabud.email.receiver.shared.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import com.zabud.email.receiver.shared.exception.InvalidEventException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event<T> {

  private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:ssZ");
  private static Function<Date, String> formatDate = date -> DATE_FORMAT.format(date);

  private HashMap<String, String> headers;
  private T data;
  private final String id;

  public Event(String type, HashMap<String, String> headers, T data) {
    if(Optional.ofNullable(type).orElse("").isEmpty()) {
     throw new InvalidEventException(String.valueOf(type));
    }
    this.id = UUID.randomUUID().toString();
    this.headers = headers;
    this.data = data;
    if(this.headers == null) {
      this.headers = new HashMap<>();
    }
    this.headers.put("CREATION_DATE", formatDate.apply(new Date()));
    this.headers.put("EVENT_TYPE", type);
  }
  
  public String getType() {
    return this.headers.get("EVENT_TYPE");
  }

  public Event(String type, T data) {
    this(type, new HashMap<String, String>(), data);
  }

  public void addHeader(String name, String value) {
    this.headers.putIfAbsent(name, value);
  }

  public void addHeader(Entry<String, String> header) {
    this.headers.putIfAbsent(header.getKey(), header.getValue());
  }

  public void addHeaders(Set<Entry<String, String>> entrySet) {
    entrySet.forEach(this::addHeader);
  }

}

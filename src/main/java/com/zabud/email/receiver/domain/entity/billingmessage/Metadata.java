package com.zabud.email.receiver.domain.entity.billingmessage;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Metadata {

  private final Map<String, String> values;

  public Metadata(Map<String, String> metadata) {
    this.values = Collections.unmodifiableMap(metadata);
  }

  public String get(String name) {
    return this.values.get(name);
  }

  public String toString() {
    return "{" + values.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
        .collect(Collectors.joining(",")) + "}";
  }



}

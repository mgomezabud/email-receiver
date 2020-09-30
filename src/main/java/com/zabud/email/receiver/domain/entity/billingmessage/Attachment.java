package com.zabud.email.receiver.domain.entity.billingmessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
  
  private String name;
  private byte[] content;
  private String contentType;
  
  public boolean isUblText() {
    return name.startsWith("zip/") && name.endsWith(".xml");
  }
  
  public String textValue() {
    return new String(content);
  }
  
  public String toString() {
    return "Attachment{name:"+name+", length:"+content.length+"contentType:"+contentType+"}";
  } 

}

package com.zabud.email.receiver.domain.entity.billingmessage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElectronicDocument {

  private String number;
  private DocumentSender sender;
  private DocumentType type;
  private String ubl;

  @Override
  public String toString() {
    return "ElectronicDocument [number=" + number + ", sender=" + sender + ", type=" + type + "]";
  }


}

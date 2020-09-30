package com.zabud.email.receiver.domain.entity.billingmessage;

import java.util.List;
import java.util.stream.Collectors;
import com.zabud.email.receiver.shared.exception.MalFormedDocumentMessageException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentMessage {

  private String id;
  private Metadata metadata;
  private ElectronicDocument document;
  private List<Attachment> attachments;
  
  public boolean hasAttachment() {
    return !this.attachments.isEmpty();
  }

  public Attachment getUblTextAttachment() {
    List<Attachment> ublAttachments =
        this.attachments.stream().filter(Attachment::isUblText).collect(Collectors.toList());
    if(ublAttachments.size() != 1) {
      throw new MalFormedDocumentMessageException(this.toString());
    }
    return ublAttachments.get(0);
  }
  
  

  public String toString() {
    return "DocumentMessage {id:" + this.id + "document:" + document + "metadata:"
        + metadata.toString() + ", attachments:" + attachments + "}";
  }

  
}

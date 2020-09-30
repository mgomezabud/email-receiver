package com.zabud.email.receiver.shared.exception;

public class UblAttachmentRequiredException extends BusinessException {

  private static final long serialVersionUID = 2820617043783719815L;

  public UblAttachmentRequiredException(String message) {
    super(ExceptionCode.UBL_ATTACHMENT_ELECTRONIC_DOCUMENT, message);
  }

}

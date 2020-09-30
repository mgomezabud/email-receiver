package com.zabud.email.receiver.shared.exception;

public class MalFormedDocumentMessageException extends BusinessException {

  private static final long serialVersionUID = 2820617043783719815L;

  public MalFormedDocumentMessageException(String message) {
    super(ExceptionCode.MALFORMED_MESSAGE, message);
  }

}

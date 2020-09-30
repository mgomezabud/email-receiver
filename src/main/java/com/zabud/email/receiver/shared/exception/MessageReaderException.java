package com.zabud.email.receiver.shared.exception;

public class MessageReaderException extends TechnicalException {

  private static final long serialVersionUID = 2820617043783719815L;

  public MessageReaderException(Throwable message) {
    super(ExceptionCode.PARSE_MESSAGE, message);
  }

}

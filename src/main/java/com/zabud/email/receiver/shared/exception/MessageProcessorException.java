package com.zabud.email.receiver.shared.exception;

public class MessageProcessorException extends TechnicalException {

  private static final long serialVersionUID = 2820617043783719815L;

  public MessageProcessorException(Throwable message) {
    super(ExceptionCode.PARSE_MESSAGE, message);
  }

}

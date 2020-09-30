package com.zabud.email.receiver.shared.exception;

public class InvalidEventProcessorException extends BusinessException {

  private static final long serialVersionUID = 2820617043783719815L;

  public InvalidEventProcessorException(String message) {
    super(ExceptionCode.EVENT_TYPE, message);
  }

}

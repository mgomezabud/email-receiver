package com.zabud.email.receiver.shared.exception;

public class InvalidEventException extends BusinessException {

  private static final long serialVersionUID = 2820617043783719815L;

  public InvalidEventException(String message) {
    super(ExceptionCode.EVENT_TYPE, message);
  }

}

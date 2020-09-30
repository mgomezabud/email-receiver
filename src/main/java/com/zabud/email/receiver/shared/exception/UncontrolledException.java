package com.zabud.email.receiver.shared.exception;

public final class UncontrolledException extends BaseException {
  
  private static final long serialVersionUID = -8946415577270136466L;

  public UncontrolledException(Throwable message) {
    super(ExceptionCode.UNCONTROLLED , message);
  }

}

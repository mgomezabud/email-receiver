package com.zabud.email.receiver.shared.exception;

public abstract class BusinessException extends BaseException {

  private static final long serialVersionUID = 1L;
  
  public BusinessException(ExceptionCode codigoException, String message) {
    super(codigoException, message);
  }
  
}

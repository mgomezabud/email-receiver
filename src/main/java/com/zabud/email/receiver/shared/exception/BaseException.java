package com.zabud.email.receiver.shared.exception;

public abstract class BaseException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;
  private final ExceptionCode codigoException;
  
  public BaseException(ExceptionCode codigoException) {
    super(codigoException.getType());
    this.codigoException = codigoException;
  }
  
  public BaseException(ExceptionCode codigoException, String message) {
    super(message);
    this.codigoException = codigoException;
  }
  
  public BaseException(ExceptionCode codigoException, Throwable message) {
    super(message);
    this.codigoException = codigoException;
  }
  
  public ExceptionCode getCodigoException() {
    return codigoException;
  }

}

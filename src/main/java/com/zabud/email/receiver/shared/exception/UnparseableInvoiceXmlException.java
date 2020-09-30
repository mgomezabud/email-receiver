package com.zabud.email.receiver.shared.exception;

public class UnparseableInvoiceXmlException extends BusinessException {

  private static final long serialVersionUID = 2820617043783719815L;

  public UnparseableInvoiceXmlException(String message) {
    super(ExceptionCode.UNPARSEABLE_INVOICE_XML_DOCUMENT, message);
  }

}

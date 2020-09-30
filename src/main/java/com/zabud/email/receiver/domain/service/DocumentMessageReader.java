package com.zabud.email.receiver.domain.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import com.zabud.email.receiver.domain.entity.billingmessage.DocumentMessage;

public interface DocumentMessageReader<T> {

  public Optional<DocumentMessage> read(T t);

  public static byte[] toByteArray(InputStream in) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len;

    while ((len = in.read(buffer)) != -1) {
      os.write(buffer, 0, len);
    }

    return os.toByteArray();
  }

}

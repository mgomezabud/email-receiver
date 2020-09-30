package com.zabud.email.receiver.infraestructure.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zabud.email.receiver.domain.entity.billingmessage.Attachment;
import com.zabud.email.receiver.domain.entity.billingmessage.DocumentMessage;
import com.zabud.email.receiver.domain.entity.billingmessage.DocumentSender;
import com.zabud.email.receiver.domain.entity.billingmessage.DocumentType;
import com.zabud.email.receiver.domain.entity.billingmessage.ElectronicDocument;
import com.zabud.email.receiver.domain.entity.billingmessage.Metadata;
import com.zabud.email.receiver.domain.service.DocumentMessageReader;
import com.zabud.email.receiver.shared.exception.MessageReaderException;
import com.zabud.email.receiver.shared.exception.UblAttachmentRequiredException;
import lombok.val;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class EmailDocumentMessageReader implements DocumentMessageReader<MimeMessage> {
  private static final int DEFAULT_BUFFER_SIZE = 1024;
  
  @Override
  public Optional<DocumentMessage> read(MimeMessage email) {
    try {
      val documentMessage = new DocumentMessage();
      documentMessage.setId(email.getMessageID());
      documentMessage.setMetadata(getMetadata(email));
      documentMessage.setAttachments(getAttachments((Multipart) email.getContent()));
      documentMessage
          .setDocument(readElectronicDocument(email, documentMessage.getUblTextAttachment()));
      return Optional.of(documentMessage);
    } catch (IOException | MessagingException e) {
      log.error(e.getMessage());
      throw new MessageReaderException(e);
    }
  }

  private Metadata getMetadata(MimeMessage email) throws MessagingException {
    Map<String, String> metadata = new HashMap<>();
    metadata.put("id", email.getMessageID());
    metadata.put("from", Arrays.toString(email.getFrom()));
    metadata.put("to", Arrays.toString(email.getAllRecipients()));
    metadata.put("subject", email.getSubject());
    return new Metadata(metadata);
  }

  private List<Attachment> getAttachments(Multipart multiPart)
      throws javax.mail.MessagingException, IOException {
    List<Attachment> attachments = new ArrayList<>();
    for (int i = 0; i < multiPart.getCount(); i++) {
      MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
      if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
        val attachment = new Attachment(part.getFileName(),
            DocumentMessageReader.toByteArray(part.getInputStream()), part.getContentType());
        List<Attachment> zipAttachments = readZip(part);
        if (zipAttachments.isEmpty()) {
          attachments.add(attachment);
        } else {
          attachments.addAll(zipAttachments);
        }
      }
    }

    return attachments;
  }

  private static List<Attachment> readZip(MimeBodyPart bodyPart)
      throws MessagingException, IOException {
    if (!bodyPart.getFileName().endsWith(".zip"))
      return Collections.emptyList();

    log.debug("descomprimir zip: {}", bodyPart.getFileName());

    ZipInputStream zis = new ZipInputStream(bodyPart.getInputStream());
    ZipEntry zipEntry = zis.getNextEntry();

    String zipFileName = "zip/" + bodyPart.getFileName().replace(".zip", "") + "/";

    List<Attachment> attachments = new ArrayList<>();
    while (zipEntry != null) {
      attachments.add(readZipEntry(zis, zipEntry, zipFileName));
      zipEntry = zis.getNextEntry();
    }
    zis.closeEntry();
    zis.close();
    return attachments;
  }

  private static Attachment readZipEntry(ZipInputStream zis, ZipEntry zipEntry, String zipFileName)
      throws IOException {
    log.debug("Leyendo entrada zip: {}", zipEntry.getName());
    ByteArrayOutputStream baos = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
    int len;
    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    while ((len = zis.read(buffer)) > 0) {
      baos.write(buffer, 0, len);
    }
    baos.close();

    return new Attachment(zipFileName + "" + zipEntry.getName(), baos.toByteArray(),
        new MimetypesFileTypeMap().getContentType(zipEntry.getName()));
  }

  private ElectronicDocument readElectronicDocument(MimeMessage email,
      Attachment attachment) {
    try {
      if (!attachment.isUblText()) {
        throw new UblAttachmentRequiredException(email.getSubject());
      }

      String[] subject = email.getSubject().split(";");
      val sender = new DocumentSender();

      sender.setNitFacturador(subject[0]);
      sender.setNombreComercialFacturador(subject[4]);
      sender.setNombreFacturador(subject[1]);
      DocumentType type = new DocumentType();
      type.setCodigoTipoDocumento(subject[3]);
      return ElectronicDocument.builder().number(subject[2]).sender(sender).type(type)
          .ubl(attachment.textValue()).build();
    } catch (Exception e) {
      throw new MessageReaderException(e);
    }

  }


  
}

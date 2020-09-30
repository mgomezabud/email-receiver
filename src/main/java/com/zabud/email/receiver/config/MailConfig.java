package com.zabud.email.receiver.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {

  @Value("${zabud.email.receiver.password}")
  private String receiverPassword;
  @Value("${zabud.email.receiver.host}")
  private String receiverHost;
  @Value("${zabud.email.receiver.user}")
  private String receiverUser;
  @Value("${zabud.email.receiver.port}")
  private String receiverPort;
  @Value("${zabud.email.receiver.mailbox}")
  private String receiverBox;

  @Value("${zabud.email.debug}")
  private String debug;

  @Bean
  public ImapMailReceiver receiver() throws UnsupportedEncodingException {
    ImapMailReceiver receiver = new ImapMailReceiver(getImapUrl());
    receiver.setJavaMailProperties(javaMailProperties());
    receiver.setCancelIdleInterval(30);
    receiver.setShouldMarkMessagesAsRead(true);
    receiver.setSimpleContent(true);
    return receiver;
  }

  private String getImapUrl() throws UnsupportedEncodingException {
    return "imaps://" + receiverUser + ":"
        + URLEncoder.encode(receiverPassword, StandardCharsets.UTF_8.toString()) + "@"
        + receiverHost + ":" + receiverPort + "/" + receiverBox;
  }

  @Bean
  @Qualifier(value = "imapMailFlow")
  public IntegrationFlow imapMailFlow() throws UnsupportedEncodingException {
    return IntegrationFlows.from(Mail.imapIdleAdapter(receiver())).channel("imapChannel").get();
  }

  private Properties javaMailProperties() {
    Properties javaMailProperties = new Properties();
    javaMailProperties.setProperty("mail.imap.socketFactory.class",
        "javax.net.ssl.SSLSocketFactory");
    javaMailProperties.setProperty("mail.imap.socketFactory.fallback", "false");
    javaMailProperties.setProperty("mail.store.protocol", "imaps");
    javaMailProperties.setProperty("mail.debug", debug);
    return javaMailProperties;
  }

}

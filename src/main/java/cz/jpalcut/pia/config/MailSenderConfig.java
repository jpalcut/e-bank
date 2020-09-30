package cz.jpalcut.pia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Konfigurace JavaMailSender
 */
@Configuration
public class MailSenderConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.default-encoding}")
    private String encoding;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.transport.protocol}")
    private String protocol;

    @Value("${spring.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.smtp.starttls.enable}")
    private String starttls;

    @Value("${spring.mail.debug}")
    private String debug;

    /**
     * Nastavení hodnot JavaMailSender
     *
     * @return JavaMailSender
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding(this.encoding);
        mailSender.setHost(this.host);
        mailSender.setPort(this.port);
        mailSender.setUsername(this.username);
        mailSender.setPassword(this.password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", this.protocol);
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.starttls.enable", this.starttls);
        props.put("mail.debug", this.debug);
        return mailSender;
    }


}

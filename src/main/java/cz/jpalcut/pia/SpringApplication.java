package cz.jpalcut.pia;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringApplication.class);
    }

    public static void main(String[] args) {

        org.springframework.boot.SpringApplication app =
                new org.springframework.boot.SpringApplication(SpringApplication.class);
        app.run(args);
    }
}
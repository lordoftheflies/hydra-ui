package hu.lordoftheflies.hydra.ui;

import hu.lordoftheflies.hydra.uaac.configuration.UaacConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
    UaacConfiguration.class,
    SecurityConfiguration.class,
    SwaggerConfiguration.class
})
public class UiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}

package be.rmangels.filemanager.jar.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"be.rmangels.filemanager.*"})
@EnableAsync
@EnableScheduling
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TestApplication.class);
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("dev");
        application.setEnvironment(environment);
        application.run(args);
    }
}

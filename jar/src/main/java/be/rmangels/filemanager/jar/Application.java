package be.rmangels.filemanager.jar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"be.rmangels.filemanager.*"})
@EnableAsync
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        System.setProperty("java.io.tmpdir", "C:\\temp");
        SpringApplication.run(Application.class, args);
    }
}

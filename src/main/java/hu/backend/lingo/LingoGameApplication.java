package hu.backend.lingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class LingoGameApplication {
    public static void main(String[] args) {

        SpringApplication.run(LingoGameApplication.class, args);

    }
}

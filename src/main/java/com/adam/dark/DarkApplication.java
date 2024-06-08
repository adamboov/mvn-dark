package com.adam.dark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author adamboov
 */
@EnableOpenApi
@SpringBootApplication
public class DarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarkApplication.class, args);
    }

}

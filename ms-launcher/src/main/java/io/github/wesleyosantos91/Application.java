package io.github.wesleyosantos91;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan({"io.github.wesleyosantos91"})
public class Application {

    public static void main(String[] args) {
        log.info("Iniciando a aplicação");
        SpringApplication.run(Application.class, args);
    }

}

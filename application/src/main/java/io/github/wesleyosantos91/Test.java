package io.github.wesleyosantos91;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
public class Test {

    @GetMapping
    public String get() {
        log.info("olá mundo!");
        return "ola";
    }
}

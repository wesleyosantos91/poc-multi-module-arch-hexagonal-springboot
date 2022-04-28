package io.github.wesleyosantos91.configuration;

import io.github.wesleyosantos91.Application;
import io.github.wesleyosantos91.adapter.PersonMySQLAdapter;
import io.github.wesleyosantos91.core.service.PersonServicePortImpl;
import io.github.wesleyosantos91.ports.api.PersonServicePort;
import io.github.wesleyosantos91.ports.spi.PersonDatabasePort;
import io.github.wesleyosantos91.repository.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class AppBeansConfig {

    @Bean
    public PersonDatabasePort personDatabasePort(PersonRepository repository) {
        return new PersonMySQLAdapter(repository);
    }

    @Bean
    public PersonServicePort personServicePort(PersonDatabasePort personDatabasePort) {
        return new PersonServicePortImpl(personDatabasePort);
    }
}

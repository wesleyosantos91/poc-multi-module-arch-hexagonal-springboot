package io.github.wesleyosantos91.ports.spi;

import io.github.wesleyosantos91.core.domain.PersonDomain;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonDatabasePort {

    Page<PersonDomain> find(PersonDomain personDomain, Pageable pageInfoDomain);
    Optional<PersonDomain> findById(Long id);
    PersonDomain exist(Long id);
    PersonDomain create(PersonDomain personDomain);
    PersonDomain update(Long id, PersonDomain personDomain);
    void delete(Long id);
}

package io.github.wesleyosantos91.ports.api;

import io.github.wesleyosantos91.core.domain.PersonDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonServicePort {

    Page<PersonDomain> find(PersonDomain personDomain, Pageable pageable);
    PersonDomain findById(Long id);
    PersonDomain exist(Long id);
    PersonDomain create(PersonDomain personDomain);
    PersonDomain update(Long id, PersonDomain personDomain);
    void delete(Long id);
}

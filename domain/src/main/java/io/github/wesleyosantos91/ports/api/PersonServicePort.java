package io.github.wesleyosantos91.ports.api;

import io.github.wesleyosantos91.core.domain.PersonDomain;
import java.util.List;

public interface PersonServicePort {

    List<PersonDomain> find();
    PersonDomain findById(Long id);
    PersonDomain exist(Long id);
    PersonDomain create(PersonDomain personDomain);
    PersonDomain update(Long id, PersonDomain personDomain);
    void delete(Long id);
}

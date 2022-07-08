package io.github.wesleyosantos91.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import io.github.wesleyosantos91.core.domain.PersonDomain;
import io.github.wesleyosantos91.core.exception.ResourceNotFoundException;
import io.github.wesleyosantos91.repository.PersonRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@DataJpaTest
@ExtendWith(SpringExtension.class)
class PersonMySQLAdapterTest {

    private PersonMySQLAdapter adapter;
    @Autowired
    private PersonRepository repository;

    @BeforeEach
    void setUp() {
        adapter = new PersonMySQLAdapter(repository);
        FixtureFactoryLoader.loadTemplates("io.github.wesleyosantos91.utils.fixture");
    }

    @Test
    @DisplayName("[datastore-mysql] - should return a page is not empty")
    void shouldReturnAPageIsNotEmpty() {
        PersonDomain personDomain = new PersonDomain();
        personDomain.setCpf("56844030932");
        Page<PersonDomain> result = adapter.find(personDomain, Pageable.unpaged());
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
    }

    @Test
    @DisplayName("[datastore-mysql] - should return a personDomain with id equals 1")
    void shouldReturnAPersonDomainWithIdEquals1() {
        Optional<PersonDomain> personDomainOptional = adapter.findById(1L);
        PersonDomain result = personDomainOptional.get();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[datastore-mysql] - should return a personDomain with id equals 1")
    void shouldReturnAPersonDomainWithIdEquals1UsingMethodExist() {
        PersonDomain result = adapter.exist(1L);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("[datastore-mysql] - should created one personDomain and return id 1")
    void shouldCreatedOnePersonDomainAndReturnId1() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("create");
        PersonDomain result = adapter.create(personDomain);
        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("[datastore-mysql] - should update one personDomain and return email change")
    void shouldUpdateOnePersonDomainAndReturnEmailChange() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("update");
        PersonDomain result = adapter.update(1L, personDomain);
        assertThat(result.getEmail()).isEqualTo("wesleyosantos91@gmail.com");
    }

    @Test
    @DisplayName("[datastore-mysql] - should delete one personDomain with id 1")
    void shouldDeleteOnePersonDomainWithId1() {
        adapter.delete(1L);
        assertThrows(ResourceNotFoundException.class, () -> {
            adapter.exist(1L);
        });
    }
}
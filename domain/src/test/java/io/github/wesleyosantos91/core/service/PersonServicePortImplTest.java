package io.github.wesleyosantos91.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import io.github.wesleyosantos91.core.domain.PersonDomain;
import io.github.wesleyosantos91.core.exception.ResourceNotFoundException;
import io.github.wesleyosantos91.ports.spi.PersonDatabasePort;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PersonServicePortImplTest {

    @InjectMocks
    private PersonServicePortImpl personServicePort;

    @Mock
    private PersonDatabasePort personDatabasePort;

    @BeforeEach
    void setUp() {
        FixtureFactoryLoader.loadTemplates("io.github.wesleyosantos91.utils.fixture");
    }

    @Test
    @DisplayName("[domain] - should return a page is not empty")
    void shouldReturnAListIsNotEmpty() {
        List<PersonDomain> personDomains = Fixture.from(PersonDomain.class).gimme(1,"valid");
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme( "valid");
        PageRequest pageRequest = PageRequest.of(1, 10);
        when(personDatabasePort.find(personDomain, pageRequest)).thenReturn(new PageImpl<>(personDomains, pageRequest, personDomains.size()));
        Page<PersonDomain> result = personServicePort.find(personDomain, pageRequest);

        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
    }

    @Test
    @DisplayName("[domain] - should return a personDomain with id equals 1")
    void shouldReturnAPersonDomainWithIdEquals1() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.findById(any())).thenReturn(Optional.of(personDomain));

        PersonDomain result = personServicePort.findById(1L);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should return a ResourceNotFoundException with message Not found regitstry with code 1")
    void shouldReturnAResourceNotFoundExceptionWithMessageNotFoundRegitstryWithCode1() {

        when(personDatabasePort.findById(any())).thenReturn(Optional.empty());

        ResourceNotFoundException result = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            personServicePort.findById(1L);
        });

        assertThat(result.getMessage()).isEqualTo("Not found regitstry with code 1");
    }

    @Test
    @DisplayName("[domain] - should return a existing personDomain with id equals 1")
    void shouldReturnAExistingPersonDomainWithIdEquals1() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.exist(any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.exist(1L);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("[domain] - should created one personDomain and return id 1")
    void shouldCreatedOnePersonDomainAndReturnId1() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.create(any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.create(personDomain);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should update one personDomain and return email change")
    void shouldUpdateOnePersonDomainAndReturnEmailChange() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid_update");
        when(personDatabasePort.update(any(), any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.update(1L, personDomain);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should delete one personDomain with id 1")
    void shouldDeleteOnePersonDomainWithId1() {

        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.findById(any())).thenReturn(Optional.of(personDomain));
        personServicePort.delete(1L);
        verify(personDatabasePort, times(1)).delete(1L);
    }
}
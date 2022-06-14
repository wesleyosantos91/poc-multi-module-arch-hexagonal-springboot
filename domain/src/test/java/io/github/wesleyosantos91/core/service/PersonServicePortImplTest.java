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
    @DisplayName("[domain] - should return a list is not empty")
    void should_return_a_list_is_not_empty() {
        List<PersonDomain> personDomains = Fixture.from(PersonDomain.class).gimme(1,"valid");
        when(personDatabasePort.find()).thenReturn(personDomains);
        List<PersonDomain> result = personServicePort.find();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("[domain] - should return a personDomain with id equals 1")
    void should_return_a_personDomain_with_id_equals_1() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.findById(any())).thenReturn(Optional.of(personDomain));

        PersonDomain result = personServicePort.findById(1L);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should return a ResourceNotFoundException with message Not found regitstry with code 1")
    void should_return_a_ResourceNotFoundException_with_message_Not_found_regitstry_with_code_1() {

        when(personDatabasePort.findById(any())).thenReturn(Optional.empty());

        ResourceNotFoundException result = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            personServicePort.findById(1L);
        });

        assertThat(result.getMessage()).isEqualTo("Not found regitstry with code 1");
    }

    @Test
    @DisplayName("[domain] - should return a existing personDomain with id equals 1")
    void should_return_a_existing_personDomain_with_id_equals_1() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.exist(any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.exist(1L);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("[domain] - should created one personDomain and return id 1")
    void should_created_one_personDomain_and_return_id_1() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.create(any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.create(personDomain);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should update one personDomain and return email change")
    void should_update_one_personDomain_and_return_email_change() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid_update");
        when(personDatabasePort.update(any(), any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.update(1L, personDomain);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should delete one personDomain with id 1")
    void should_delete_one_personDomain_with_id_1() {

        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.findById(any())).thenReturn(Optional.of(personDomain));
        personServicePort.delete(1L);
        verify(personDatabasePort, times(1)).delete(1L);
    }
}
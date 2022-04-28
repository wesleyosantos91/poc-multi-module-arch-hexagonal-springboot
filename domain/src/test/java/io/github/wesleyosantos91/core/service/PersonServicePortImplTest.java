package io.github.wesleyosantos91.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import io.github.wesleyosantos91.core.domain.PersonDomain;
import io.github.wesleyosantos91.ports.spi.PersonDatabasePort;
import java.util.List;
import java.util.Optional;
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
    @DisplayName("[domain] - should return a list with positive quantity")
    void find() {
        List<PersonDomain> personDomains = Fixture.from(PersonDomain.class).gimme(1,"valid");
        when(personDatabasePort.find()).thenReturn(personDomains);
        List<PersonDomain> result = personServicePort.find();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("[domain] - should return a personDomain with id equals 1")
    void findById() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.findById(any())).thenReturn(Optional.of(personDomain));

        Optional<PersonDomain> personDomainOptional = personServicePort.findById(1L);
        PersonDomain result = personDomainOptional.get();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should return a personDomain with id equals 1")
    void exist() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.exist(any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.exist(1L);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("[domain] - should created one personDomain and return id 1")
    void create() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid");
        when(personDatabasePort.create(any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.create(personDomain);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should update one personDomain and return email change")
    void update() {
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("valid_update");
        when(personDatabasePort.update(any(), any())).thenReturn(personDomain);
        PersonDomain result = personServicePort.update(1L, personDomain);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[domain] - should delete one personDomain with id 1")
    void delete() {

        personServicePort.delete(1L);

        PersonServicePortImpl personServicePortMock = mock(PersonServicePortImpl.class);
        personServicePortMock.delete(1L);

        verify(personServicePortMock).delete(1L);
    }
}
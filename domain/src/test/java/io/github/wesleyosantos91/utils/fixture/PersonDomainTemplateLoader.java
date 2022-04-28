package io.github.wesleyosantos91.utils.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import io.github.wesleyosantos91.core.domain.PersonDomain;
import java.time.LocalDate;

public class PersonDomainTemplateLoader implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(PersonDomain.class).addTemplate("valid", new Rule(){{
            add("id", 1L);
            add("name", "wesley");
            add("dateOfBirth", LocalDate.of(1991, 6, 12));
            add("cpf", "03669252100");
            add("email", "${name}@gmail.com");
        }});

        Fixture.of(PersonDomain.class).addTemplate("valid_update", new Rule(){{
            add("id", 1L);
            add("name", "wesley");
            add("dateOfBirth", LocalDate.of(1991, 6, 12));
            add("cpf", "03669252100");
            add("email", "${name}osantos91@gmail.com");
        }});

        Fixture.of(PersonDomain.class).addTemplate("invalid", new Rule(){{
            add("id", null);
            add("name", null);
            add("dateOfBirth", null);
            add("cpf", null);
            add("email", null);
        }});
    }
}

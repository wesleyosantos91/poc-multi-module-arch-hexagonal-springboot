package io.github.wesleyosantos91.utils.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import io.github.wesleyosantos91.api.v1.request.PersonRequest;
import java.time.LocalDate;

public class PersonRequestTemplateLoader implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(PersonRequest.class).addTemplate("create", new Rule(){{
            add("name", "wesley");
            add("dateOfBirth", LocalDate.of(1991, 6, 12));
            add("cpf", "03669252100");
            add("email", "${name}@gmail.com");
        }});

        Fixture.of(PersonRequest.class).addTemplate("update", new Rule(){{
            add("name", "wesley");
            add("dateOfBirth", LocalDate.of(1991, 6, 12));
            add("cpf", "03669252100");
            add("email", "${name}osantos91@gmail.com");
        }});

        Fixture.of(PersonRequest.class).addTemplate("invalid", new Rule(){{
            add("name", null);
            add("dateOfBirth", null);
            add("cpf", null);
            add("email", null);
        }});
    }
}

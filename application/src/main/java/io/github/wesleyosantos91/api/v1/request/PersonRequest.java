package io.github.wesleyosantos91.api.v1.request;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest implements Serializable {

    private String name;
    private LocalDate dateOfBirth;
    private String cpf;
    private String email;
}

package io.github.wesleyosantos91.api.v1.response;

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
public class PersonResponse implements Serializable {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String cpf;
    private String email;
}

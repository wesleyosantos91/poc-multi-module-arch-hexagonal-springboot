package io.github.wesleyosantos91.api.v1.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonQueryRequest implements Serializable {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String cpf;
    private String email;
}

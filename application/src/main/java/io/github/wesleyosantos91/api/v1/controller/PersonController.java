package io.github.wesleyosantos91.api.v1.controller;

import static io.github.wesleyosantos91.api.v1.mapper.PersonHttpMapper.INSTANCE;
import static java.text.MessageFormat.format;

import io.github.wesleyosantos91.api.v1.request.PersonRequest;
import io.github.wesleyosantos91.api.v1.response.PersonResponse;
import io.github.wesleyosantos91.core.exception.ResourceNotFoundException;
import io.github.wesleyosantos91.ports.api.PersonServicePort;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/persons")
public class PersonController {

    private final PersonServicePort personServicePort;

    public PersonController(PersonServicePort personServicePort) {
        this.personServicePort = personServicePort;
    }

    @PostMapping
    public ResponseEntity<PersonResponse> create(@Valid @RequestBody PersonRequest request) {
        log.info("Function started 'create person'");
        var person = personServicePort.create(INSTANCE.toDomain(request));
        log.info("finished function with sucess 'create person'");
        return ResponseEntity.status(HttpStatus.CREATED).body(INSTANCE.toResponse(person));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonResponse> getById(@PathVariable Long id) {
        log.info("Function started 'getById person'");
        var person = personServicePort.findById(id);
        log.info("finished function with sucess 'getById person'");
        return ResponseEntity.ok().body(INSTANCE.toResponse(person));
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> find() {
        log.info("Function started 'find person'");
        var domains = personServicePort.find();
        log.info("finished function with sucess 'find person'");
        return ResponseEntity.ok().body(INSTANCE.toListResponse(domains));
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<PersonResponse> update(@PathVariable Long id, @RequestBody PersonRequest request) {
        log.info("Function started 'update person'");
        var person = personServicePort.update(id, INSTANCE.toDomain(request));
        log.info("finished function with sucess 'update person'");
        return ResponseEntity.status(HttpStatus.OK).body(INSTANCE.toResponse(person));
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Function started 'delete person'");
        personServicePort.delete(id);
        log.info("finished function with sucess 'delete person'");
        return ResponseEntity.noContent().build();
    }
}

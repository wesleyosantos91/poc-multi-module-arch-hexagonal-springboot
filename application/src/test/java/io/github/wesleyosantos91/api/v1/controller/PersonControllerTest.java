package io.github.wesleyosantos91.api.v1.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wesleyosantos91.api.v1.request.PersonRequest;
import io.github.wesleyosantos91.core.domain.PersonDomain;
import io.github.wesleyosantos91.ports.api.PersonServicePort;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@ActiveProfiles("test")
@WebMvcTest(PersonController.class)
@ExtendWith(SpringExtension.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServicePort personServicePort;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        FixtureFactoryLoader.loadTemplates("io.github.wesleyosantos91.utils.fixture");
    }

    @Test
    @DisplayName("[application] - should created one personDomain and return id 1")
    void should_created_one_personDomain_and_return_id_1() throws Exception {


        PersonRequest personRequest = Fixture.from(PersonRequest.class).gimme("create");
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("created");

        String jsonBody = objectMapper.writeValueAsString(personRequest);

        when(personServicePort.create(any())).thenReturn(personDomain);

        ResultActions result =
                mockMvc.perform(post("/v1/persons")
                        .content(jsonBody).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("[application] - should return a personDomain with id equals 1")
    void should_return_a_personDomain_with_id_equals_1() throws Exception {

        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("created");
        when(personServicePort.findById(any())).thenReturn(personDomain);

        ResultActions result =
                mockMvc.perform(get("/v1/persons/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("[application] - should return a list is not empty")
    void should_return_a_list_is_not_empty() throws Exception {

        List<PersonDomain> personDomains = Fixture.from(PersonDomain.class).gimme(1,"created");
        when(personServicePort.find()).thenReturn(personDomains);

        ResultActions result =
                mockMvc.perform(get("/v1/persons")
                        .accept(MediaType.APPLICATION_JSON));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$").isArray());
        result.andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("[application] - should update one personDomain and return email change")
    void should_update_one_personDomain_and_return_email_change() throws Exception {

        PersonRequest personRequest = Fixture.from(PersonRequest.class).gimme("create");
        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("created");

        String jsonBody = objectMapper.writeValueAsString(personRequest);

        when(personServicePort.findById(any())).thenReturn(personDomain);

        when(personServicePort.update(any(), any())).thenReturn(personDomain);

        ResultActions result =
                mockMvc.perform(put("/v1/persons/{id}", 1L)
                        .content(jsonBody).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("[application] - should delete one personDomain with id 1")
    void should_delete_one_personDomain_with_id_1() throws Exception {

        PersonDomain personDomain = Fixture.from(PersonDomain.class).gimme("created");
        when(personServicePort.findById(any())).thenReturn(personDomain);
        doNothing().when(personServicePort).delete(any());

        ResultActions result =
                mockMvc.perform(delete("/v1/persons/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(status().isNoContent());
        verify(personServicePort, times(1)).delete(1L);
    }
}
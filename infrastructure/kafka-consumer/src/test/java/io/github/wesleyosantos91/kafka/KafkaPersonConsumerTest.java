package io.github.wesleyosantos91.kafka;

import io.github.wesleyosantos91.schema.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class KafkaPersonConsumerTest {

    @Value("${app.kafka.topic}")
    private String TOPIC;
    
    @Autowired
    private KafkaPersonConsumer kafkaPersonConsumer;

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    @AfterEach
    void tearDown() {
        kafkaPersonConsumer.resetLatch();
    }

    @Test
    @DisplayName("[kafka] - should consumed one event and validated is attributes")
    void shouldConsumedOneEventAndValidatedIsAttributes() throws Exception  {

        String messagekey = UUID.randomUUID().toString();
        Person person = Person.newBuilder()
                .setDateOfBirth(LocalDate.now())
                .setEmail("wesleyosantos91@gmail.com")
                .setCpf("00000000000")
                .setName("wesley")
                .build();

        Message<Person> message = MessageBuilder.withPayload(person)
                .setHeader("hash", person.hashCode())
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(1L))
                .setHeader("type", "fct")
                .setHeader("cid", messagekey)
                .setHeader(KafkaHeaders.TOPIC, this.TOPIC)
                .setHeader(KafkaHeaders.KEY, messagekey)
                .build();

        kafkaTemplate.send(message);

        boolean messageConsumed  = kafkaPersonConsumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        Person result = kafkaPersonConsumer.getPayload();
        assertThat(result.getName()).isEqualTo(person.getName());
        assertThat(result.getName()).isEqualTo(person.getName());
        assertThat(result.getEmail()).isEqualTo(person.getEmail());
        assertThat(result.getCpf()).isEqualTo(person.getCpf());
        assertThat(result.getDateOfBirth()).isEqualTo(person.getDateOfBirth());
    }
}
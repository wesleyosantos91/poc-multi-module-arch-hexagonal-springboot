package io.github.wesleyosantos91.kafka;

import io.github.wesleyosantos91.core.exception.BusinessException;
import io.github.wesleyosantos91.schema.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaPersonProducer {

    @Value("${app.kafka.topic}")
    private String TOPIC;

    private final KafkaTemplate<String, Person> kafkaTemplate;

    public void send(Person person) {
        try {

            UUID uuid = UUID.randomUUID();
            Message<Person> message = createMessageWithHeaders(uuid, person);

            this.kafkaTemplate.send(message).whenComplete((result, ex) -> {
                if (Objects.nonNull(ex)) {
                    log.error("Failed to send event {}, with messageID {}", ex.getCause(), uuid);
                    throw new RuntimeException(ex);
                }

                log.info("Success to send event {}, with messageID {}", result.getRecordMetadata().toString(), uuid);
            });

        } catch (BusinessException ex) {
            log.error(ex.getMessage(), ex);
            throw new BusinessException(ex.getLocalizedMessage(), ex);
        }
    }

    private Message<Person> createMessageWithHeaders(UUID uuid, Person person) {
        return MessageBuilder.withPayload(person)
                .setHeader("hash", person.hashCode())
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(1L))
                .setHeader("type", "fct")
                .setHeader("cid", uuid.toString())
                .setHeader(KafkaHeaders.TOPIC, this.TOPIC)
                .setHeader(KafkaHeaders.KEY, uuid.toString())
                .build();
    }

}

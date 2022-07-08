package io.github.wesleyosantos91.kafka;

import io.github.wesleyosantos91.core.exception.BusinessException;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import io.github.wesleyosantos91.schema.Person;
import org.springframework.util.concurrent.ListenableFutureCallback;

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


            ListenableFutureCallback<SendResult<String, Person>> futureCallback = new ListenableFutureCallback<SendResult<String, Person>>() {

                @Override
                public void onFailure(Throwable throwable) {
                    log.error("Failed to send event {}, with messageID {}", throwable.getCause(), uuid);
                    throw new RuntimeException(throwable);
                }

                @Override
                public void onSuccess(SendResult<String, Person> sendResult) {
                    log.info("Success to send event {}, with messageID {}", sendResult.getRecordMetadata().toString(), uuid);
                }
            };

            this.kafkaTemplate.send(message).addCallback(futureCallback);

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
                .setHeader(KafkaHeaders.MESSAGE_KEY, uuid.toString())
                .build();
    }

}

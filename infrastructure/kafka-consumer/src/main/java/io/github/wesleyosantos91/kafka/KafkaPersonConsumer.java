package io.github.wesleyosantos91.kafka;

import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import io.github.wesleyosantos91.schema.Person;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class KafkaPersonConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private Person payload;

    @KafkaListener(id = "main-kafka-listener", topics = "${app.kafka.topic}")
    public void consumer(@Payload ConsumerRecord<String, Person> consumerRecord, Acknowledgment ack) throws Exception {
        try {

            log.info("process record {}", consumerRecord.value());

            log.info("key: " + consumerRecord.key());
            log.info("Headers: " + consumerRecord.headers());
            log.info("topic: " + consumerRecord.topic());
            log.info("Partion: " + consumerRecord.partition());
            log.info("Person: " + consumerRecord.value());

            payload = consumerRecord.value();
            latch.countDown();

        } finally {
            ack.acknowledge();
        }
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}

package io.github.wesleyosantos91.kafka;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.github.wesleyosantos91.kafka.utils.CustomKafkaAvroDeserializer;
import io.github.wesleyosantos91.schema.Person;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class KafkaPersonProducerTest {

    @Value("${app.kafka.topic}")
    private String TOPIC;
    @Autowired
    private KafkaPersonProducer producer;

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    @Autowired
    private KafkaProperties kafkaProperties;

    private Consumer<String, Person> consumer;

    @BeforeEach
    void setUp() {
        //consumidores usados no código de teste precisam ser criados assim no código porque senão não funcionará
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("in-test-consumer", "false", kafkaEmbedded));
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomKafkaAvroDeserializer.class);
        configs.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        configs.put("schema.registry.url", "not-used");

        consumer = new DefaultKafkaConsumerFactory<String, Person>(configs).createConsumer("in-test-consumer", "10");

        kafkaProperties.buildConsumerProperties();

        consumer.subscribe(Lists.newArrayList(this.TOPIC));
    }

    @AfterEach
    public void reset() {
        consumer.close();
    }

    @Test
    @DisplayName("[kafka] - should producer one event and consumed one event and validated is attributes")
    void shouldProducerOneEventAndConsumedOneEventAndValidatedIsAttributes() {
        Person person = Person.newBuilder()
                .setDateOfBirth(LocalDate.now())
                .setEmail("wesleyosantos91@gmail.com")
                .setCpf("00000000000")
                .setName("wesley")
                .build();

        producer.send(person);

        ConsumerRecord<String, Person> result = KafkaTestUtils.getSingleRecord(consumer, this.TOPIC);
        assertThat(result).isNotNull();
        assertThat(result.value().getName()).isEqualTo(person.getName());
        assertThat(result.value().getEmail()).isEqualTo(person.getEmail());
        assertThat(result.value().getCpf()).isEqualTo(person.getCpf());
        assertThat(result.value().getDateOfBirth()).isEqualTo(person.getDateOfBirth());
    }
}
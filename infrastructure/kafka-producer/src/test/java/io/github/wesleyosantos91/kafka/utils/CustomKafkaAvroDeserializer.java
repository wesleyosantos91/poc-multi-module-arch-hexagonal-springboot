package io.github.wesleyosantos91.kafka.utils;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.github.wesleyosantos91.schema.Person;
import org.apache.avro.Schema;

public class CustomKafkaAvroDeserializer extends KafkaAvroDeserializer {

    @Override
    public Object deserialize(String topic, byte[] bytes) {

        this.schemaRegistry = getMockClient(Person.SCHEMA$);
        return super.deserialize(topic, bytes);
    }

    private static SchemaRegistryClient getMockClient(final Schema schema$) {
        return new MockSchemaRegistryClient() {

            @Override
            public synchronized Schema getById(int id) {
                return schema$;
            }
        };
    }
}

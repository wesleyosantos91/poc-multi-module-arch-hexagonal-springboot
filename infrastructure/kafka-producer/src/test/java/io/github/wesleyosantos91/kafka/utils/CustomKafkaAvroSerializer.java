package io.github.wesleyosantos91.kafka.utils;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.Map;

public class CustomKafkaAvroSerializer extends KafkaAvroSerializer {
    public CustomKafkaAvroSerializer() {
        super();
        super.schemaRegistry = new MockSchemaRegistryClient();
    }

    public CustomKafkaAvroSerializer(SchemaRegistryClient client) {
        super(new MockSchemaRegistryClient());
    }

    public CustomKafkaAvroSerializer(SchemaRegistryClient client, Map<String, ?> props) {
        super(new MockSchemaRegistryClient(), props);
    }
}
package server.resources;

import api.EventReturn;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

@Path("/event/{requestID}")
@Produces(MediaType.APPLICATION_JSON)
public class BatteryEventResource {

    @GET
    public EventReturn eventCheck() {
        return new EventReturn("Event Works");
    }

    @POST
    public EventReturn processBatteryEvent(@PathParam("requestID") String requestID, String content) {
        KafkaProducer producer = null;
        Properties props = new Properties();

        try {
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
            props.put("schema.registry.url", "http://localhost:8090");
            File avroSchema = new File(this.getClass().getResource("/battery/battery-event-schema.json").toURI());
            producer = new KafkaProducer(props);
            Schema schema = new Schema.Parser().parse(avroSchema);
            GenericRecord avroRecord = new GenericData.Record(schema);
            avroRecord.put("device_id", requestID);
            String key = requestID;

            ProducerRecord<Object, Object> record = new ProducerRecord<>("topic1", key, avroRecord);
            producer.send(record);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            producer.flush();
            producer.close();
        }


        System.out.println("content: " + content);
        return new EventReturn(content);
    }
}

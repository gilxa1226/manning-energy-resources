package server.resources;

import api.EventReturn;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import model.BatteryEvent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Path("/event/{requestID}")
@Produces(MediaType.APPLICATION_JSON)
public class BatteryEventResource {

    private String BOOTSTRAP_SERVERS_CONFIG;
    private String SCHEMA_REGISTRY_URL;

    public BatteryEventResource(String BOOTSTRAP_SERVERS_CONFIG, String SCHEMA_REGISTRY_URL) {
        this.BOOTSTRAP_SERVERS_CONFIG = BOOTSTRAP_SERVERS_CONFIG;
        this.SCHEMA_REGISTRY_URL = SCHEMA_REGISTRY_URL;
    }

    @GET
    public EventReturn eventCheck() {
        return new EventReturn("Event Works");
    }

    @POST
    public EventReturn processBatteryEvent(@PathParam("requestID") String requestID, String content) {
        KafkaProducer producer = null;
        Properties props = new Properties();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        BatteryEvent event;
        GenericRecord avroRecord;

        try {
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
            props.put("schema.registry.url", SCHEMA_REGISTRY_URL);
            File avroSchema = new File(this.getClass().getResource("/battery/battery-event-schema.json").toURI());
            producer = new KafkaProducer(props);
            Schema schema = new Schema.Parser().parse(avroSchema);

            String key = requestID;
            String[] split = content.split("\n");
            for (int x = 0; x < split.length; x++) {
                event = mapper.readValue(split[x], BatteryEvent.class);
                avroRecord = new GenericData.Record(schema);
                avroRecord.put("device_id", event.getDevice_id());
                avroRecord.put("processor1_temp", event.getProcessor1_temp());
                avroRecord.put("processor2_temp", event.getProcessor2_temp());
                avroRecord.put("processor3_temp", event.getProcessor3_temp());
                avroRecord.put("processor4_temp", event.getProcessor4_temp());
                avroRecord.put("charging_source", event.getCharging_source());
                avroRecord.put("charging", event.getCharging());
                avroRecord.put("current_capacity", event.getCurrent_capacity());
                avroRecord.put("inverter_state", event.getInverter_state());
                avroRecord.put("moduleL_temp", event.getModuleL_temp());
                avroRecord.put("moduleR_temp", event.getModuleR_temp());
                avroRecord.put("SoC_regulator", event.getSoC_regulator());
                ProducerRecord<String, Object> record = new ProducerRecord<>("topic1", key, avroRecord);
                producer.send(record);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            producer.flush();
            producer.close();
        }

        return new EventReturn(content);
    }
}

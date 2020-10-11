package server.resources;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import raw.battery.RawRecord;
import server.db.DeviceDAO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static org.apache.commons.io.IOUtils.toByteArray;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceEndpoint {

    protected final KafkaProducer producer;
    private final DeviceDAO db;
    private final String table;
    protected final String topic;

    public DeviceEndpoint(KafkaProducer producer, String topic, DeviceDAO db, String table) {
        this.producer = producer;
        this.topic = topic;
        this.db = db;
        this.table = table;
    }

    @GET
    @Path("/state")
    public Response getStatus(@QueryParam("uuid") String uuid) {
        return Response.ok().entity(db.getDeviceState(table, uuid)).build();
    }

    @POST
    @Path("/send/{uuid}")
    @Consumes({APPLICATION_OCTET_STREAM, APPLICATION_JSON})
    @Produces(APPLICATION_JSON)
    public Response send(@PathParam("uuid") String uuid, @Context HttpServletRequest request)
        throws ExecutionException, InterruptedException, IOException {

        ByteBuffer body = ByteBuffer.wrap(toByteArray(request.getInputStream()));
        RawRecord payload = new RawRecord(uuid, Instant.now().toEpochMilli(), body);

        ProducerRecord record = new ProducerRecord(topic, uuid, payload);
        Future<RecordMetadata> metadata = producer.send(record);

        return Response.ok().entity(serialize(metadata.get())).build();
    }

    protected Map<String, Object> serialize(RecordMetadata metadata) {
        return ImmutableMap.<String, Object>builder()
                .put("offset", metadata.offset())
                .put("partition", metadata.partition())
                .put("topic", metadata.topic())
                .put("timestamp", metadata.timestamp())
                .build();
    }
}

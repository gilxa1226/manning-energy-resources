package server;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jdbi.v3.core.Jdbi;
import server.configuration.GridServerConfiguration;
import server.db.DeviceDAO;
import server.resources.DeviceEndpoint;

import java.util.Properties;

public class GridServer extends Application<GridServerConfiguration> {

    @Override
    public void initialize(Bootstrap<GridServerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(GridServerConfiguration configuration,
                    Environment environment) throws Exception {

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "device-db");

        KafkaProducer producer = createProducer(configuration);
        environment.lifecycle().manage(new CloseableManaged(producer));

        environment.jersey().register(
                new DeviceEndpoint(producer, configuration.getTopic(), jdbi.onDemand(DeviceDAO.class), configuration.getDeviceTable())
        );

        // Needed to serve static web pages from root.
        // it namesapces all the API endpoints under /api
        environment.jersey().setUrlPattern("/api");
    }

    private KafkaProducer createProducer(GridServerConfiguration conf) {
        Properties props = new Properties();
        // Reasonable defaults
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        // overrides
        props.putAll(conf.getKafka());
        return new KafkaProducer(props);
    }
}

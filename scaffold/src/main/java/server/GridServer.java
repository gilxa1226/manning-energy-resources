package server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import server.configuration.GridServerConfiguration;
import server.resources.BatteryEventResource;
import server.resources.HelloWorldResource;

public class GridServer extends Application<GridServerConfiguration> {

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<GridServerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(GridServerConfiguration configuration,
                    Environment environment) {
        final HelloWorldResource helloWorldResource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

        final BatteryEventResource batteryEventResource = new BatteryEventResource();

        environment.jersey().register(helloWorldResource);
        environment.jersey().register(batteryEventResource);

    }
}

package processor;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class App<T extends StreamConfiguration> extends Application<T> {
    @Override
    public void run(T configuration, Environment environment) throws Exception {
        // Noop, nothing interesting needs to be setup.
    }
}

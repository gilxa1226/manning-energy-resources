package server;

import io.dropwizard.lifecycle.Managed;

import java.io.Closeable;

public class CloseableManaged implements Managed {

    private final Closeable closeable;

    public CloseableManaged(Closeable closeable) {
        this.closeable = closeable;
    }

    @Override
    public void start() throws Exception {
        // noop
    }

    @Override
    public void stop() throws Exception {
        closeable.close();
    }
}

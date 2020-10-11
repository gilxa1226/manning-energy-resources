package server.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import processor.StreamConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DeviceStateStreamConf extends StreamConfiguration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
    private String deviceTable;

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("deviceTable")
    public String getDeviceTable() {
        return deviceTable;
    }

    @JsonProperty("deviceTable")
    public void setDeviceTable(String deviceTable) {
        this.deviceTable = deviceTable;
    }
}

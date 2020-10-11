package server.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class GridServerConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
    private String deviceTable;

    @NotNull
    private String topic;
    @NotNull
    private Map<String, String> kafka = new HashMap<>();
    @NotNull
    private int maxMessageSize;
    @NotNull
    private String s3Bucket;

    @NotNull
    private String awsAccessKey;
    @NotNull
    private String awsSecretKey;


    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("topic")
    public String getTopic() {
        return topic;
    }

    @JsonProperty("topic")
    public void setTopic(String Topic) {
        topic = Topic;
    }

    @JsonProperty("kafka")
    public Map<String, String> getKafka(){
        return this.kafka;
    }

    @JsonProperty("kafka")
    public void setKafka(Map<String, String> kafka) {
        this.kafka = kafka;
    }

    @JsonProperty("deviceTable")
    public String getDeviceTable() {
        return deviceTable;
    }

    @JsonProperty("deviceTable")
    public void setDeviceTable(String deviceTable) {
        this.deviceTable = deviceTable;
    }

    @JsonProperty("maxMessageSize")
    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    @JsonProperty("maxMessageSize")
    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    @JsonProperty("s3Bucket")
    public String getS3Bucket() {
        return s3Bucket;
    }

    @JsonProperty("s3Bucket")
    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    @JsonProperty("awsAccessKey")
    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    @JsonProperty("awsAccessKey")
    public void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
    }

    @JsonProperty("awsSecretKey")
    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    @JsonProperty("awsSecretKey")
    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }
}

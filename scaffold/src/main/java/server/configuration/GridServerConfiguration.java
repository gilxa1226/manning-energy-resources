package server.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class GridServerConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName;

    @NotEmpty
    private String bootstrapServersConfig;

    @NotEmpty
    private String schemaRegistryUrl;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public String getBootstrapServersConfig() { return bootstrapServersConfig; }

    @JsonProperty
    public void setBootstrapServersConfig(String bootstrapServersConfig) { this.bootstrapServersConfig = bootstrapServersConfig; }

    @JsonProperty
    public String getSchemaRegistryUrl() { return schemaRegistryUrl; }

    @JsonProperty
    public void setSchemaRegistryUrl(String schemaRegistryUrl) { this.schemaRegistryUrl = schemaRegistryUrl; }
}

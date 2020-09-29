package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class EventReturn {
    @Length(max = 3)
    private String content;

    public EventReturn() {
        // Jackson deserialization
    }

    public EventReturn(String content) {
        this.content = content;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}

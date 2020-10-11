package server.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import org.apache.avro.generic.GenericData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BytesToJson implements Function<ByteBuffer, List<Map<String, Object>>> {

    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE =
            new TypeReference<Map<String, Object>>() {};
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public List<Map<String, Object>> apply(ByteBuffer bytes) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteBufferBackedInputStream(bytes)));
            List<Map<String, Object>> results = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                Map<String, Object> result = MAPPER.readValue(line, MAP_TYPE_REFERENCE);
                results.add(result);
                line = reader.readLine();
            }
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

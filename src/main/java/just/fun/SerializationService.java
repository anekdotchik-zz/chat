package just.fun;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SerializationService {
    private ObjectMapper mapper;

    public SerializationService() {
        this.mapper = new ObjectMapper();
    }

    public String serialize(Object obj) throws SerializationException {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, obj);
            return writer.toString();
        } catch (JsonGenerationException e) {
            throw new SerializationException("Error json generation", e);
        } catch (JsonMappingException e) {
            throw new SerializationException("Error json mappings", e);
        } catch (IOException e) {
            throw new SerializationException("Error working with stream", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}

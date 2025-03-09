package com.blbulyandavbulyan.booksearch.service.jackson;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JacksonPropertyNamesResolver {
    private final ObjectMapper objectMapper;

    @SneakyThrows// if we fail, we fail, then probably something wrong in the application code itself
    public List<String> getPropertyNamesFor(Class<?> inputClass) {
        SerializerProvider serializerProvider = objectMapper.getSerializerProviderInstance();
        JsonSerializer<Object> serializer = serializerProvider.findValueSerializer(inputClass);
        Iterator<PropertyWriter> logicalProperties = serializer.properties();
        List<String> fields = new ArrayList<>();
        while (logicalProperties.hasNext()) {
            PropertyWriter propertyWriter = logicalProperties.next();
            String jsonFieldName = propertyWriter.getName();
            fields.add(jsonFieldName);
        }
        return fields;
    }
}

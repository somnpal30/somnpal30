package com.sample.springrest.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sample.springrest.model.LoginVO;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class CustomHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    private ObjectMapper initCustomObjectMapper() {
        ObjectMapper customObjectMapper = new ObjectMapper();
        return customObjectMapper;
    }

    private ObjectMapper initiatePrettyObjectMapper() {
        ObjectMapper customObjectMapper = new ObjectMapper();
        customObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // additional indentation for arrays
        DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
        pp.indentArraysWith(new DefaultIndenter());
        customObjectMapper.setDefaultPrettyPrinter(pp);

        PropertyFilter encryptionFilter = new SimpleBeanPropertyFilter() {
            @Override
            public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer)
                    throws Exception {
                if (include(writer)) {
                    if (!writer.getName().equals("password")) {
                        writer.serializeAsField(pojo, jgen, provider);
                        return;
                    }
                    String encValue = ((LoginVO) pojo).getPassword();
                   /* if (intValue > 0) {
                        writer.serializeAsField(pojo, jgen, provider);
                    }*/
                } else if (!jgen.canOmitFields()) {
                    writer.serializeAsOmittedField(pojo, jgen, provider);
                }
            }

            @Override
            protected boolean include(BeanPropertyWriter writer) {
                return true;
            }

            @Override
            protected boolean include(PropertyWriter writer) {
                return true;
            }
        };

        FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false).addFilter("encryptFilter", encryptionFilter);
        customObjectMapper.setFilterProvider(filters);

        return customObjectMapper;
    }
}

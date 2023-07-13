package com.sample.springrest.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sample.springrest.model.LoginVO;
import com.sample.springrest.security.RSAUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;



//@JsonComponent
public class CustomJsonConverter {


    static RSAUtil rsaUtil ;
    public static class Serialize extends JsonSerializer<LoginVO> {


        @Override
        public void serialize(LoginVO value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

            try {
                value.setPassword(rsaUtil.decrypt(value.getPassword()));
                value.setUsername(rsaUtil.decrypt(value.getUsername()));
            } catch (IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException |
                     NoSuchPaddingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Deserialize extends JsonDeserializer<Object> {

        @Override
        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            System.out.println("*****");
            return null;
        }
    }
}

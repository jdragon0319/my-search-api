package com.example.mysearchapi.infra.rest;

import com.example.mysearchapi.infra.rest.ExternalApiResponse;
import com.example.mysearchapi.infra.rest.KakaoRestApiResponse;
import com.example.mysearchapi.infra.rest.NaverRestApiResponse;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ExternalApiResponseDeserializer extends JsonDeserializer<ExternalApiResponse> {

    @Override
    public ExternalApiResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode jsonNode = mapper.readTree(p);

        if (jsonNode.get("documents") != null) {
            return mapper.readValue(jsonNode.toString(), KakaoRestApiResponse.class);
        }

        if (jsonNode.get("items") != null) {
            return mapper.readValue(jsonNode.toString(), NaverRestApiResponse.class);
        }

        return null;
    }

}

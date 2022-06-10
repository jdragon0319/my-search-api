package com.example.mysearchapi.common.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class CollectLogger {
    public static String toJsonStr(Map jsonMap) {
        String jsonStr = "";
        try {
            jsonStr = new ObjectMapper().writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}

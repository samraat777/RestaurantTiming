package com.sam.RestaurantTiming.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JsonMapper {
    String jsonString;

    public JsonMapper(String jsonString) {
        this.jsonString = jsonString;
    }

    public ScheduleEntity mapJsonToObject() {
        ObjectMapper objectMapper = new ObjectMapper();
        ScheduleEntity scheduleEntity;

        try {
            scheduleEntity = objectMapper.readValue(this.jsonString, ScheduleEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return scheduleEntity;
    }
}

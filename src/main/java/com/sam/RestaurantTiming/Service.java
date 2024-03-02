package com.sam.RestaurantTiming;

import com.sam.RestaurantTiming.DTO.ScheduleEntity;

import java.util.Map;


public interface Service {
    public Map processSchedule(String data);
    public Map getReadableTime(ScheduleEntity scheduleEntity,Map<String,String> map) throws Exception;
    public String getTimeInAMPMFormat(int value);
}

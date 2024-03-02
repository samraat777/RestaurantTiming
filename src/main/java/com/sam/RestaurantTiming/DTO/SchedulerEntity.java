package com.sam.RestaurantTiming.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SchedulerEntity {
    String day;
    TimingEntity timingEntity;
}

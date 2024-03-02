package com.sam.RestaurantTiming.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleEntity {
    private List<TimingEntity> monday;
    private List<TimingEntity> tuesday;
    private List<TimingEntity> wednesday;
    private List<TimingEntity> thursday;
    private List<TimingEntity> friday;
    private List<TimingEntity> saturday;
    private List<TimingEntity> sunday;

}

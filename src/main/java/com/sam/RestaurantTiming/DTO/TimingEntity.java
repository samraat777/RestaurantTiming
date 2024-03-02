package com.sam.RestaurantTiming.DTO;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimingEntity {
    private String type;
    private int value;
}

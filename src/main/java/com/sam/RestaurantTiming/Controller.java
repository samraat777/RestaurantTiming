package com.sam.RestaurantTiming;

import com.sam.RestaurantTiming.DTO.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Component
@RestController
@RequestMapping("/restaurant")

    public class Controller {
    ServiceIMPL serviceIMPL;
    private String payload;
@GetMapping("/timing")
    public ResponseEntity<Map> schedule(@RequestBody String payload){
        this.payload = payload;
        serviceIMPL = new ServiceIMPL();
        Map response = serviceIMPL.processSchedule(payload);

        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
}

}

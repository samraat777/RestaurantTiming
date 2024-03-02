package com.sam.RestaurantTiming;

import com.sam.RestaurantTiming.DTO.JsonMapper;
import com.sam.RestaurantTiming.DTO.ScheduleEntity;
import com.sam.RestaurantTiming.DTO.SchedulerEntity;
import com.sam.RestaurantTiming.DTO.TimingEntity;

import java.text.SimpleDateFormat;
import java.util.*;


@org.springframework.stereotype.Service
public class ServiceIMPL implements Service {
    @Override
    public Map processSchedule(String data) {
        /*
         *Process Json to object
         *Process object to get valid timing
         */
        ScheduleEntity scheduleEntity = null;
        JsonMapper jsonMapper = new JsonMapper(data);
        scheduleEntity = jsonMapper.mapJsonToObject();


        //TODO: Processing Logic on schedule object
        Map<String, String> result = new LinkedHashMap<>();
        String[] days = {Constants.MONDAY, Constants.TUESDAY, Constants.WEDNESDAY, Constants.THURSDAY, Constants.FRIDAY, Constants.SATURDAY, Constants.SUNDAY,};
        for (String day : days) {
            result.put(day, Constants.CLOSE);
        }
        try {
            result = getReadableTime(scheduleEntity, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /*
    DS: Stack <<Day :string , CurrentTimingEntity : timingentity>>
    Logic - when [ in stack
    And we get this ] pop and store result in closing day tag

    Edge case - say we start from monday and found one closing tag in it before any opening
    When ] open  comes -- > do nothing --> just store in stack and
    at last in stack we should have one Open tag ] and [  then pop these and mark it in
    The closing tag day result
    Expected Result format
    Monday: Closed Tuesday: 10 AM - 6 PM Wednesday: Closed
    Thursday: 10:30 AM - 6 PM Friday: 10 AM - 1 AM Saturday: 10 AM - 1 AM Sunday: 12 PM - 9 PM

    Result structure
    [Key] ->{1,2,3}
    List<LIst<String>> monday - [] ;
    List<LIst<String>> tuesday -[10 AM - 1 AM],[5 AM , 10 AM] -> when we add time just process and add time as the format 10 Am - 1 AM

    Result processing -> Mon Sun list ans
    {
    for mondayResultArray
    Monday : String 1, string2
    Tues :
    }
     */
    @Override
    public Map getReadableTime(ScheduleEntity scheduleEntity, Map<String, String> ansMap) throws Exception {

        Map<String, List<TimingEntity>> currentDayToSchedulesMapping = getStringListMap(scheduleEntity);
        Stack<SchedulerEntity> stack = new Stack<>();
        //Day - Time String

        for (String currentDay : currentDayToSchedulesMapping.keySet()) {
            System.out.println("Current Day is --" + currentDay);
            List<TimingEntity> schedules = currentDayToSchedulesMapping.get(currentDay);
            for (TimingEntity schedule : schedules) {
                if (stack.isEmpty()) {
                    stack.add(new SchedulerEntity(currentDay, schedule));
                    System.out.println(stack.toString());
                } else {
                    SchedulerEntity sch = stack.peek();
                    String openingDay = sch.getDay();
                    //When we have a closing time of shop
                    if (sch.getTimingEntity().getType().toLowerCase().equals(Constants.OPEN) && schedule.getType().toLowerCase().equals(Constants.CLOSE)) {

                        String timeOpen = getTimeInAMPMFormat(sch.getTimingEntity().getValue());
                        String timeClose = getTimeInAMPMFormat(schedule.getValue());
                        String openCloseTime = timeOpen + " - " + timeClose;
                        ansMap = addScheduleToAnswer(ansMap, openCloseTime, openingDay);
                        stack.pop();
                    } else {
                        stack.add(new SchedulerEntity(currentDay, schedule));
                    }
                }
            }
        }
//        Stack is not empty means --say -- We have started from MONDAY, but we found a closing time at first on monday,
//        so the last working day of week must have opening tag
        if (!stack.isEmpty() && stack.size() == 2) {
            SchedulerEntity openSchedule = stack.pop();
            SchedulerEntity closingSchedule = stack.pop();
            int openTime = openSchedule.getTimingEntity().getValue();
            int closeTime = closingSchedule.getTimingEntity().getValue();

            String openTimeStr = getTimeInAMPMFormat(openTime);
            String closeTimeStr = getTimeInAMPMFormat(closeTime);

            String openCloseTime = openTimeStr + " - " + closeTimeStr;

            //String ansSchedule;
            String openingDay = openSchedule.getDay();
            ansMap = addScheduleToAnswer(ansMap, openCloseTime, openingDay);

            if (!stack.isEmpty()) {
                throw new Exception("Data Format Issue");
            }
        }
        return ansMap;
    }

    private Map<String, String> addScheduleToAnswer(Map<String, String> ansMap, String openCloseTime, String openingDay) {
        if (ansMap.containsKey(openingDay) && ansMap.get(openingDay) != Constants.CLOSE) {
            String currTimeString = ansMap.get(openingDay);
            currTimeString = currTimeString + ", " + openCloseTime;
            ansMap.put(openingDay, currTimeString);
            //System.out.println(openingDay + "--" +currTimeString);
        } else {
            // currTimeString = openCloseTime;
            ansMap.put(openingDay, openCloseTime);
            //System.out.println(openingDay + "--" +currTimeString);
        }
        return ansMap;
    }

    private static Map<String, List<TimingEntity>> getStringListMap(ScheduleEntity scheduleEntity) {
        Map<String, List<TimingEntity>> currentDayToScheduleMapping = new LinkedHashMap<>();
        List<TimingEntity> monday, tuesday, wednesday, thursday, friday, saturday, sunday;

        monday = scheduleEntity.getMonday();
        tuesday = scheduleEntity.getTuesday();
        wednesday = scheduleEntity.getWednesday();
        thursday = scheduleEntity.getThursday();
        friday = scheduleEntity.getFriday();
        saturday = scheduleEntity.getSaturday();
        sunday = scheduleEntity.getSunday();

        currentDayToScheduleMapping.put(Constants.MONDAY, monday);
        currentDayToScheduleMapping.put(Constants.TUESDAY, tuesday);
        currentDayToScheduleMapping.put(Constants.WEDNESDAY, wednesday);
        currentDayToScheduleMapping.put(Constants.THURSDAY, thursday);
        currentDayToScheduleMapping.put(Constants.FRIDAY, friday);
        currentDayToScheduleMapping.put(Constants.SATURDAY, saturday);
        currentDayToScheduleMapping.put(Constants.SUNDAY, sunday);
        return currentDayToScheduleMapping;
    }

    public String getTimeInAMPMFormat(int value) {
        SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
        String timeFormatted = formatTime.format(new Date(value * 1000));
        return timeFormatted;
    }
}

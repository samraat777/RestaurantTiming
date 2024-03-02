package com.sam.RestaurantTiming;

import com.sam.RestaurantTiming.DTO.ScheduleEntity;
import com.sam.RestaurantTiming.DTO.TimingEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.Assert.*;


@SpringBootTest
class RestaurantTimingApplicationTests {



    @Autowired
	ServiceIMPL serviceIMPL;

	@Test
	void contextLoads() {
		//assertThat(serviceIMPL).isNotNull();
	}

	@DisplayName("Unix to current date conversion")
	@Test
	public void testTimeConversion()
	{
		//ServiceIMPL serviceIMPL = new ServiceIMPL();
		String time = "03.30 pm";
		assertEquals(time,serviceIMPL.getTimeInAMPMFormat(36000));
	}


	@DisplayName("Getting Readable Time")
	@Test
	public void GettingReadableTime()
	{
		//ScheduleEntity scheduleEntity, Map<String, String> ansMap
		 List<TimingEntity> monday = new ArrayList<TimingEntity>();
		 List<TimingEntity> tuesday = new ArrayList<TimingEntity>(Arrays.asList(new TimingEntity("open",36000),new TimingEntity("close",64800)));
		 List<TimingEntity> wednesday = new ArrayList<>();
		ScheduleEntity scheduleEntity = getScheduleEntity(monday, tuesday, wednesday);
		Map<String,String> answer = new LinkedHashMap<>();
		try{
			answer = serviceIMPL.getReadableTime(scheduleEntity,answer);
		}catch (Exception e)
		{
			System.out.println(e.toString());
		}




		/*
		 expectedAnswer
		"Monday": "close",
		"Tuesday": "03.30 pm - 11.30 pm",
		"Wednesday": "close",
		"Thursday": "04.00 pm - 11.30 pm",
		"Friday": "03.30 pm - 06.30 am",
		"Saturday": "03.30 pm - 06.30 am",
		"Sunday": "05.30 pm - 02.30 am"
		 */
		assertEquals(answer.get(Constants.SUNDAY),"05.30 pm - 02.30 am");
		assertEquals(answer.get(Constants.THURSDAY),"04.00 pm - 11.30 pm");
		try{
			assert(answer.get(Constants.MONDAY).equals("03.30 pm - 11.30 pm"));
		}catch(Exception e)
		{
			assertTrue(e.getClass().getSimpleName().equals("NullPointerException"));
		}
	}

	private static ScheduleEntity getScheduleEntity(List<TimingEntity> monday, List<TimingEntity> tuesday, List<TimingEntity> wednesday) {
		List<TimingEntity> thursday = new ArrayList<>(Arrays.asList(new TimingEntity("open", 37800), new TimingEntity("close", 64800)));
		List<TimingEntity> friday = new ArrayList<>(Arrays.asList(new TimingEntity("open", 36000)));
		List<TimingEntity> saturday = new ArrayList<>(Arrays.asList(new TimingEntity("close", 3600), new TimingEntity("open", 36000)));
		List<TimingEntity> sunday = new ArrayList<>(Arrays.asList(new TimingEntity("close", 3600), new TimingEntity("open", 43200), new TimingEntity("close", 75600)));
		ScheduleEntity scheduleEntity = new ScheduleEntity(monday, tuesday, wednesday,thursday,friday,saturday,sunday);
		return scheduleEntity;
	}

}

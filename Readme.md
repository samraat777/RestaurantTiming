Problem Statement

**Restaurant Opening Hours**

In short
Your task is to write an endpoint that accepts JSON-formatted opening hours of a restaurant as an input and returns the rendered human readable format as a text output.
Input data

Input JSON consists of keys indicating days of a week and corresponding opening hours as values. One JSON input includes data for one restaurant.

{

day of week: opening hours

day of week: opening hours 

...
}

day of week: monday / tuesday / wednesday / thursday / friday / saturday / sunday 

opening hours: an array of objects containing opening hours. Each object consists of two keys:

● type: open or close

● value: opening / closing time as UNIX time (1.1.1970 as a date),
e.g. 32400 = 9 AM, 37800 = 10:30 AM, max value is 86399 = 11:59:59 PM

Example: on Mondays a restaurant is open from 9 AM to 8 PM
{

"monday" : [
{
"type" : "open",
"value" : 32400 },
{
"type" : "close",
"value" : 72000 }
], ....
}


Special cases

● If a restaurant is closed the whole day, an array of opening hours is empty.

    ○ “tuesday”: [] means a restaurant is closed on Tuesdays
● A restaurant can be opened and closed multiple times during the same day,

    ○ E.g. on Mondays from 9 AM - 11 AM and from 1 PM to 5 PM
● A restaurant might not be closed during the same day

    ○ A restaurant can be opened e.g. on a Friday evening and closed early
Saturday morning. In that case friday-object includes only the opening
time. Closing time is part of the saturday-object.

    ○ When printing opening hours which span between multiple days, closing time is always a part of the day when a restaurant was opened (e.g. Friday 8 PM - 1 AM)
{
"friday" : [
{
"type" : "open",
"value" : 64800 }
], “saturday”: [
{ "type" : "close",
"type" : "close", "value" : 3600 A restaurant is open:
}, Friday: 6 PM - 1 AM
{ Saturday: 9 AM - 11 AM, 4 PM - "type" : "open", "value" : 32400 }, 11 PM
{
"value" : 39600 },
{

"type" : "open",
"value" : 57600 },
{
"type" : "close",
"value" : 82800 }
] }

**Deliverable** 

Part 1

Build a HTTP API that accepts opening hours data as an input (JSON) and returns a more human readable version of the data formatted using a 12-hour clock.

Output example in 12-hour clock format:

Monday: 8 AM - 10 AM, 11 AM - 6 PM 

Tuesday: Closed

Wednesday: 11 AM - 6 PM

Thursday: 11 AM - 6 PM

Friday: 11 AM - 9 PM 

Saturday: 11 AM - 9 PM 

Sunday: Closed

Return the formatted version in HTTP response.

Part 2

Tell us what you think about the data format. Is the current JSON structure the best way to represent that kind of data or can you come up with a better version? There are no right answers here. Please write your thoughts to readme.md.

Full JSON Example Input
{
"monday" : [],
"tuesday" : [
{
"type" : "open",
"value" : 36000 },
{
"type" : "close",
"value" : 64800 }
],
"wednesday" : [],

"thursday" : [
{
"type" : "open",
"value" : 37800 },
{
"type" : "close",
"value" : 64800 }
],
"friday" : [
{
"type" : "open",
"value" : 36000 }
],
"saturday" : [
{
"type" : "close",
"value" : 3600 },
{
"type" : "open",
"value" : 36000 }
],
"sunday" : [
{
"type" : "close",
"value" : 3600 },
{
"type" : "open",
"value" : 43200 },
{
"type" : "close",
"value" : 75600 }
] }


**Output**

Monday: Closed 

Tuesday: 10 AM - 6 PM 

Wednesday: Closed

Thursday: 10:30 AM - 6 PM 

Friday: 10 AM - 1 AM 

Saturday: 10 AM - 1 AM 

Sunday: 12 PM - 9 PM



########################################################################################################
**Solution** 



API Signature -- http://localhost:8080/restaurant/timing

Curl Request -

curl --location --request GET 'http://localhost:8080/restaurant/timing' \
--header 'Content-Type: text/plain' \
--data '{
"monday": [],
"tuesday": [
{
"type": "open",
"value": 36000
},
{
"type": "close",
"value": 64800
}
],
"wednesday": [],
"thursday": [
{
"type": "open",
"value": 37800
},
{
"type": "close",
"value": 64800
}
],
"friday": [
{
"type": "open",
"value": 36000
}
],
"saturday": [
{
"type": "close",
"value": 3600
},
{
"type": "open",
"value": 36000
}
],
"sunday": [
{
"type": "close",
"value": 3600
},
{
"type": "open",
"value": 43200
},
{
"type": "close",
"value": 75600
}
]
}'


**Json Structure Enhancement**

**We can have one base class **days** that will have schedule for each of the day,
And for Each day we have one or more opening/closing schedules.**

Eg below structure

{
"days": [
{
"monday": [
{
"type": "open",
"value": 36000
},
{
"type": "close",
"value": 64800
}
]
},
{
"tuesday": [
{
"type": "open",
"value": 36000
},
{
"type": "close",
"value": 64800
}
]
}
]
}






**Few Point for code and used data structures**
* There is one more challenge that we were facing while scheduling for days, that when having opening and closing days on differnt days.
* For solving this I have used **stack data structure** and made a custom class that will have <DayName,SingleSchedule>.
* Each entry in stack is not dependent on days, just tag/value  - open or close is important. 
* If we have open tag at top of stack and we are having closing tag next we will pop the open schedule and take the day and time of opening and the closing
* Add it into an answer map that will have schedule for the opening day.
* There will be edge case where we will have closing tag at top of stack
* In that scenario, process other days schedule first
* At end of we will have 2 schedules, open on top and close at bottom
* This will happen because of cyclic nature of days MONDAY - SUNDAY - MONDAY
* Just pop these 2  open and close schedule and add to open day schedule answer
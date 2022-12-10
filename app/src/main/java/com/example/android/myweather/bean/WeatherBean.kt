package com.example.android.myweather.bean

data class WeatherBean(
    val city: String,
    val cityEn: String,
    val cityid: String,
    val country: String,
    val countryEn: String,
    val `data`: List<Data>,
    val update_time: String
)

data class Data(
    val air: String,
    val air_level: String,
    val air_tips: String,
    val alarm: Alarm,
    val date: String,
    val day: String,
    val hours: List<Hour>,
    val humidity: String,
    val index: List<Index>,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    val tem: String,
    val tem1: String,
    val tem2: String,
    val visibility: String,
    val wea: String,
    val wea_day: String,
    val wea_day_img: String,
    val wea_img: String,
    val wea_night: String,
    val wea_night_img: String,
    val week: String,
    val win: List<String>,
    val win_meter: String,
    val win_speed: String
)

data class Alarm(
    val alarm_content: String,
    val alarm_level: String,
    val alarm_type: String
)

data class Hour(
    val hours: String,
    val tem: String,
    val wea: String,
    val wea_img: String,
    val win: String,
    val win_speed: String
)

data class Index(
    val desc: String,
    val level: String,
    val title: String
)
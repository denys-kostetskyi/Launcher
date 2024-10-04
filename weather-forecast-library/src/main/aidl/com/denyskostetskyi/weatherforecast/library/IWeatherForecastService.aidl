// IWeatherForecastService.aidl
package com.denyskostetskyi.weatherforecast.library;

// Declare any non-default types here with import statements
import com.denyskostetskyi.weatherforecast.library.Location;
import com.denyskostetskyi.weatherforecast.library.HourlyWeatherForecast;

interface IWeatherForecastService {
    HourlyWeatherForecast getHourlyWeatherForecast(Location location, String dateTime);
}
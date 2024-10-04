// IWeatherForecastService.aidl
package com.denyskostetskyi.weatherforecast.library;

// Declare any non-default types here with import statements
import com.denyskostetskyi.weatherforecast.library.domain.model.Location;
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast;

interface IWeatherForecastService {
    @nullable HourlyWeatherForecast getHourlyWeatherForecast(in Location location, String dateTime);
}
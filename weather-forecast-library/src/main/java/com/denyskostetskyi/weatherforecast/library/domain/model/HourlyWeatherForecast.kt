package com.denyskostetskyi.weatherforecast.library.domain.model

import android.os.Parcel
import android.os.Parcelable

data class HourlyWeatherForecast(
    val dateTime: String,
    val temperature: Double,
    val weather: Weather,
    val location: Location,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        Weather.valueOf(parcel.readString() ?: Weather.UNKNOWN.name),
        parcel.readParcelable(Location::class.java.classLoader) ?: Location("", 0.0, 0.0)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dateTime)
        parcel.writeDouble(temperature)
        parcel.writeParcelable(location, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HourlyWeatherForecast> {
        override fun createFromParcel(parcel: Parcel): HourlyWeatherForecast {
            return HourlyWeatherForecast(parcel)
        }

        override fun newArray(size: Int): Array<HourlyWeatherForecast?> {
            return arrayOfNulls(size)
        }
    }
}

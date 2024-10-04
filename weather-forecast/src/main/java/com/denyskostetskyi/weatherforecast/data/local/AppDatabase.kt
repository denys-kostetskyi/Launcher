package com.denyskostetskyi.weatherforecast.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.denyskostetskyi.weatherforecast.data.local.entity.HourlyWeatherForecastEntity
import com.denyskostetskyi.weatherforecast.data.local.entity.converter.WeatherConverter

@Database(entities = [HourlyWeatherForecastEntity::class], version = 1, exportSchema = false)
@TypeConverters(WeatherConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherForecastDao(): WeatherForecastDao

    companion object {
        private const val DATABASE_NAME = "database.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build().also { instance = it }
            }
        }
    }
}

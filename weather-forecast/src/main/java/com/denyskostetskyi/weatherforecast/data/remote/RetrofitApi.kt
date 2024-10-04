package com.denyskostetskyi.weatherforecast.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(OpenMeteoApiConstants.BASE_URL)
        .build()

    val openMeteoApiService: OpenMeteoApiService by lazy {
        retrofit.create(OpenMeteoApiService::class.java)
    }
}

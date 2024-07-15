package com.inqlab.countries.api

import com.inqlab.countries.model.CityResponse
import com.inqlab.countries.model.MuseumResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey = "gvloSjuIbqB3u0EDg5OrFcIOG2ac74gAgi46NVEYweHnNfaEcF6yajYAxC8M"

interface CityService {

    @GET("museum/cities")
    suspend fun getAllCities() : Response<CityResponse>

    @GET("museum/cities")
    suspend fun getAllDistrict( @Query("city") city : String) : Response<CityResponse>

    @GET("museum")
    suspend fun getAllMuseums(@Query("city") city : String, @Query("district") district : String,) : Response<MuseumResponse>


}
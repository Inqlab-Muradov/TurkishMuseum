package com.inqlab.countries.repository

import com.inqlab.countries.api.CityService
import com.inqlab.countries.api.apiKey
import retrofit2.Response
import javax.inject.Inject

class CountryRepository@Inject constructor(
    val api : CityService
) {

    suspend fun getCities()= safeApiRequest {
        api.getAllCities()
    }


    suspend fun getDistricts(cityName: String) = safeApiRequest {
        api.getAllDistrict(city = cityName)
    }

    suspend fun getMuseums(cityName: String,districtName:String) = safeApiRequest {
        api.getAllMuseums(city = cityName, district = districtName)
    }

    private suspend fun<T> safeApiRequest(apiCall:suspend()->Response<T>) : NetworkResponse<T>{
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful){
                response.body()?.let {
                    return NetworkResponse.Success(it)
                } ?: return NetworkResponse.Error("Null body")
            }else{
                return NetworkResponse.Error(response.errorBody().toString())
            }
        }catch (e : Exception){
            return NetworkResponse.Error(e.localizedMessage.toString())
        }
    }

}
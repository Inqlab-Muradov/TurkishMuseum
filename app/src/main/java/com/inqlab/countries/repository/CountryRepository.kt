package com.inqlab.countries.repository

import com.inqlab.countries.api.CityService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class CountryRepository@Inject constructor(
    val api : CityService,
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



    private suspend fun<T> safeApiRequest(apiCall:suspend()->Response<T>) : Flow<NetworkResponse<T & Any>> =
        flow {
            emit(NetworkResponse.Loading())
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful){
                    response.body()?.let {
                        emit(NetworkResponse.Success(it))
                    } ?: emit(NetworkResponse.Error("empty Response"))
                }else{
                    emit(NetworkResponse.Error(response.errorBody().toString()))
                }
            }catch (e : Exception){
                emit(NetworkResponse.Error(e.localizedMessage.toString()))
            }
        }.flowOn(Dispatchers.IO)




}
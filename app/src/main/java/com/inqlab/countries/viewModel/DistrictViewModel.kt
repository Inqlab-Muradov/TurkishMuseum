package com.inqlab.countries.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inqlab.countries.api.CityService
import com.inqlab.countries.api.apiKey
import com.inqlab.countries.model.Data
import com.inqlab.countries.repository.CountryRepository
import com.inqlab.countries.repository.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DistrictViewModel @Inject constructor(
    val repository : CountryRepository
) : ViewModel() {

    val districtList = MutableLiveData<List<Data>>()
    val errorMessage = MutableLiveData<String>()
    val isEmpty = MutableLiveData<Boolean>()


    fun getAllDistrict(city : String){
        viewModelScope.launch {
            val response = repository.getDistricts(city)
            when(response){
                is NetworkResponse.Success->{
                    response.data?.data?.let {
                        if(it.isNotEmpty()){
                            districtList.value = it
                        }else{
                            isEmpty.value = true
                        }
                    }
                }
                is NetworkResponse.Error->{
                    response.message?.let {
                      errorMessage.value  = it

                    }
                }
            }
        }
    }
}
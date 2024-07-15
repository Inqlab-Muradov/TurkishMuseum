package com.inqlab.countries.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inqlab.countries.api.CityService
import com.inqlab.countries.model.Data
import com.inqlab.countries.repository.CountryRepository
import com.inqlab.countries.repository.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    val cityList = MutableLiveData<List<Data>>()
    val errorMessage = MutableLiveData<String>()

    init {
        getAllCity()
    }

    fun getAllCity(){
      viewModelScope.launch {
          val response = repository.getCities()
          when(response){
              is NetworkResponse.Success-> {
                  response?.data?.data?.let {
                      cityList.value = it
                  }
              }
              is NetworkResponse.Error->{
                  response.message?.let {
                      errorMessage.value = it
                  }
              }
          }

      }
    }
}
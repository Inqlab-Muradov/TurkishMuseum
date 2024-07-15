package com.inqlab.countries.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inqlab.countries.api.CityService
import com.inqlab.countries.api.apiKey
import com.inqlab.countries.model.DataX
import com.inqlab.countries.repository.CountryRepository
import com.inqlab.countries.repository.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MuseumViewModel @Inject constructor(
    val repository : CountryRepository
) : ViewModel() {

    val museumList = MutableLiveData<List<DataX>>()
    var isEmpty = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMuseum(city:String,district:String){
       viewModelScope.launch {
           val response = repository.getMuseums(city,district)
           when(response){
               is NetworkResponse.Success->{
                   response.data?.data?.let {
                      if (it.isNotEmpty()){
                          museumList.value = it
                      }else{
                          isEmpty.value = true
                      }
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
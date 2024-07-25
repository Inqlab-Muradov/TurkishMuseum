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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MuseumViewModel @Inject constructor(
    val repository : CountryRepository
) : ViewModel() {

    val uiState = MutableLiveData<MuseumUiState>()
    fun getAllMuseum(city:String,district:String){
        viewModelScope.launch {
            repository.getMuseums(city,district).collectLatest {
                when(it){
                    is NetworkResponse.Success->{
                        it.data?.data?.let {
                            uiState.value = MuseumUiState.MuseumList(it)
                        }
                    }
                    is NetworkResponse.Error->{
                        it.message?.let {
                            uiState.value = MuseumUiState.Error(it)
                        }
                    }
                    is NetworkResponse.Loading->{
                        uiState.value = MuseumUiState.Loading
                    }
                }
            }
        }
    }
}

sealed class MuseumUiState(){
    data class MuseumList(val list:List<DataX>):MuseumUiState()
    object Loading : MuseumUiState()
    data class Error(val message:String):MuseumUiState()
}
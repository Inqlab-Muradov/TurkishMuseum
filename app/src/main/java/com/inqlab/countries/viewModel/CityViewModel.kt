package com.inqlab.countries.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inqlab.countries.model.Data
import com.inqlab.countries.repository.CountryRepository
import com.inqlab.countries.repository.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    val cityUiState = MutableLiveData<UiState>()
    init {
        getAllCity()
    }

    fun getAllCity(){
      viewModelScope.launch {
          repository.getCities().collectLatest {
              when(it){
                  is NetworkResponse.Success->{
                      it.data?.data?.let {
                          cityUiState.value = UiState.list(it)
                      }
                  }
                  is NetworkResponse.Error->{
                      it.message?.let {
                        cityUiState.value = UiState.errorMessage(it)
                      }
                  }
                  is NetworkResponse.Loading->{
                      cityUiState.value = UiState.loading
                  }
              }
          }
      }
    }
}


sealed class UiState(){
    data class list(val list:List<Data>) : UiState()
    object loading : UiState()
    data class errorMessage(val message:String) : UiState()
}
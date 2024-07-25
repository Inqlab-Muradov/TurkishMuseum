package com.inqlab.countries.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inqlab.countries.repository.CountryRepository
import com.inqlab.countries.repository.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DistrictViewModel @Inject constructor(
    val repository : CountryRepository
) : ViewModel() {

    val districtUiState = MutableLiveData<UiState>()
    fun getAllDistrict(city : String){
        viewModelScope.launch {
            repository.getDistricts(city).collectLatest {
                when (it){
                    is NetworkResponse.Success->{
                        it.data?.data?.let {
                            districtUiState.value =UiState.list(it)
                        }
                    }
                    is NetworkResponse.Error->{
                        it.message?.let {
                            districtUiState.value = UiState.errorMessage(it)
                        }
                    }
                    is NetworkResponse.Loading->{
                        districtUiState.value = UiState.loading
                    }
                }
            }
        }
    }
}


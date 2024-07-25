package com.inqlab.countries.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.inqlab.countries.base.BaseFragment
import com.inqlab.countries.adapter.CityAdapter
import com.inqlab.countries.viewModel.CityViewModel
import com.inqlab.countries.databinding.FragmentCityBinding
import com.inqlab.countries.viewModel.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : BaseFragment<FragmentCityBinding>(FragmentCityBinding::inflate) {

    private val viewModel by viewModels<CityViewModel> ()
    private val cityAdapter = CityAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cityRV.adapter = cityAdapter
        observeData()
        cityAdapter.onClick = {
            findNavController().navigate(CityFragmentDirections.actionCityFragmentToDistrictFragment(it))
        }
    }

    private fun observeData(){
        viewModel.cityUiState.observe(viewLifecycleOwner){
            when(it){
                is UiState.list->{
                    cityAdapter.updateList(it.list)
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is UiState.loading->binding.progressBar.visibility = View.VISIBLE
                is UiState.errorMessage-> Toast.makeText(this.context,"${it.message}",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
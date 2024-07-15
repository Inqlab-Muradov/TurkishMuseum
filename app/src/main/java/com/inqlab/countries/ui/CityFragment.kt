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
        viewModel.cityList.observe(viewLifecycleOwner){
            cityAdapter.updateList(it)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        }
    }

}
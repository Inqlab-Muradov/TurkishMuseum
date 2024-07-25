package com.inqlab.countries.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inqlab.countries.base.BaseFragment
import com.inqlab.countries.adapter.DistrictAdapter
import com.inqlab.countries.viewModel.DistrictViewModel
import com.inqlab.countries.databinding.FragmentDistrictBinding
import com.inqlab.countries.viewModel.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DistrictFragment : BaseFragment<FragmentDistrictBinding>(FragmentDistrictBinding::inflate) {

    private val args : DistrictFragmentArgs by navArgs()
    private val districtAdapter = DistrictAdapter()
    private val viewModel by viewModels<DistrictViewModel> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.districtRV.adapter = districtAdapter
        viewModel.getAllDistrict(args.city)
        observeData()
        districtAdapter.onClick={
            findNavController().navigate(DistrictFragmentDirections.actionDistrictFragmentToMuseumFragment(it,args.city))
        }
    }

    private fun observeData(){
        viewModel.districtUiState.observe(viewLifecycleOwner){
            when(it){
                is UiState.list->{
                    districtAdapter.updateList(it.list)
                    binding.progressBarDistrict.visibility = View.INVISIBLE
                }
                is UiState.errorMessage-> Toast.makeText(this.context,"${it.message}",Toast.LENGTH_SHORT).show()
                is UiState.loading-> binding.progressBarDistrict.visibility = View.VISIBLE
            }
        }
    }
}
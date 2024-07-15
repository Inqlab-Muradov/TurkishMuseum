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
        viewModel.districtList.observe(viewLifecycleOwner){
            districtAdapter.updateList(it)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        }
        viewModel.isEmpty.observe(viewLifecycleOwner){
            if (it) binding.districtEmpty.text = "There is no any districts"
        }
    }

}
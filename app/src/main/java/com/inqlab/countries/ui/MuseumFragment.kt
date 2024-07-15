package com.inqlab.countries.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.inqlab.countries.adapter.MuseumAdapter
import com.inqlab.countries.base.BaseFragment
import com.inqlab.countries.databinding.FragmentMuseumBinding
import com.inqlab.countries.viewModel.MuseumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MuseumFragment : BaseFragment<FragmentMuseumBinding>(FragmentMuseumBinding::inflate){

    private val args : MuseumFragmentArgs by navArgs()

    private val viewModel by viewModels<MuseumViewModel> ()
    private val museumAdapter = MuseumAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musemRv.adapter = museumAdapter
        viewModel.getAllMuseum(args.city,args.district)
        observeData()
    }

    private fun observeData(){
        viewModel.museumList.observe(viewLifecycleOwner){
            museumAdapter.updateList(it)
        }
        viewModel.isEmpty.observe(viewLifecycleOwner){
            if (it) binding.emptyTxt.text = "Hic bir muze yok!!"
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        }
    }
}
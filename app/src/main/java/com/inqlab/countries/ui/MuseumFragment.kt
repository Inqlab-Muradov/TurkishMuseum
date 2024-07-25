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
import com.inqlab.countries.viewModel.MuseumUiState
import com.inqlab.countries.viewModel.MuseumViewModel
import com.inqlab.countries.viewModel.UiState
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
        viewModel.uiState.observe(viewLifecycleOwner){
            when(it){
                is MuseumUiState.MuseumList->{
                    if (it.list.isNotEmpty()) museumAdapter.updateList(it.list) else binding.emptyTxt.text = "There is not any museum"
                    binding.progressBarMuseum.visibility = View.INVISIBLE
                }
                is MuseumUiState.Error-> Toast.makeText(this.context, "${it.message}", Toast.LENGTH_SHORT).show()
                is MuseumUiState.Loading-> binding.progressBarMuseum.visibility = View.VISIBLE
            }
        }
    }
}
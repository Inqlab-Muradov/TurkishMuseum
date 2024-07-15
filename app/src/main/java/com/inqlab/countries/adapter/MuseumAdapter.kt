package com.inqlab.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inqlab.countries.databinding.FragmentMuseumBinding
import com.inqlab.countries.databinding.MuseumItemBinding
import com.inqlab.countries.model.DataX
import java.text.ParseException

class MuseumAdapter : RecyclerView.Adapter<MuseumAdapter.MuseumViewHolder>() {

    private var museumList = ArrayList<DataX>()

    inner class MuseumViewHolder(val binding : MuseumItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuseumViewHolder {
        val view = MuseumItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MuseumViewHolder(view)
    }

    override fun getItemCount(): Int {
       return museumList.size
    }

    override fun onBindViewHolder(holder: MuseumViewHolder, position: Int) {
        val item = museumList[position]
        holder.binding.museumItem = item
    }

    fun updateList(newList : List<DataX>){
        museumList.clear()
        museumList.addAll(newList)
        notifyDataSetChanged()
    }
}
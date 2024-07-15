package com.inqlab.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inqlab.countries.databinding.DistrictItemBinding
import com.inqlab.countries.model.Data

class DistrictAdapter : RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder>() {

    private val districtList = ArrayList<Data>()
    lateinit var onClick : (String)->Unit

    inner class DistrictViewHolder(val binding : DistrictItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        val view = DistrictItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DistrictViewHolder(view)
    }

    override fun getItemCount(): Int {
        return districtList.size
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        val item = districtList[position]
        holder.binding.districtItem = item
        holder.binding.districtTxt.setOnClickListener {
            onClick(item.cities.toString())
        }
    }

    fun updateList(newList : List<Data>){
        districtList.clear()
        districtList.addAll(newList)
        notifyDataSetChanged()
    }
}
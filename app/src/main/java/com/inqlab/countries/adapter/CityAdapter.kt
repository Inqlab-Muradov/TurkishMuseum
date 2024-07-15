package com.inqlab.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inqlab.countries.databinding.CityItemBinding
import com.inqlab.countries.model.Data

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val cityList = ArrayList<Data>()
    lateinit var onClick : (String)-> Unit

    inner class CityViewHolder(val binding : CityItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = CityItemBinding.inflate(LayoutInflater.from(parent.context),parent,false )
        return CityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = cityList[position]
        holder.binding.cityItem  = item
        holder.binding.cityText.setOnClickListener {
            onClick(item.cities.toString())
        }
    }

    fun updateList(newList : List<Data>){
        cityList.clear()
        cityList.addAll(newList)
        notifyDataSetChanged()
    }
}
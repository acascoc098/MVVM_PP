package com.example.proyectopersonalizado.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.databinding.ItemHotelBinding
import com.example.proyectopersonalizado.models.Hotel

class AdapterHotel(
    private var listHotel: List<Hotel>,
    private val deleteOnClick: (Int) -> Unit,
    private val updateOnClick: (Int) -> Unit,
    private val onInfoClickListener: (Hotel) -> Unit
) : RecyclerView.Adapter<ViewHHotel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHHotel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemHotelBinding.inflate(layoutInflater, parent, false)
        return ViewHHotel(itemBinding, deleteOnClick, updateOnClick, onInfoClickListener)
    }

    override fun onBindViewHolder(holder: ViewHHotel, position: Int) {
        holder.renderize(listHotel[position], position)
    }

    override fun getItemCount(): Int = listHotel.size

    fun updateData(newList: List<Hotel>) {
        listHotel = newList
        notifyDataSetChanged()
    }





}

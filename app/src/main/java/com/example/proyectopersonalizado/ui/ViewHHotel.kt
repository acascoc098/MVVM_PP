package com.example.proyectopersonalizado.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectopersonalizado.databinding.ItemHotelBinding
import com.example.proyectopersonalizado.models.Hotel


class ViewHHotel(
    private val binding: ItemHotelBinding,
    private val deleteOnClick: (Int) -> Unit,
    private val updateOnClick: (Int) -> Unit,
    private val onInfoClickListener: (Hotel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun renderize(hotel : Hotel, position: Int){
        binding.txtviewName.setText(hotel. name)
        binding.txtviewCity.setText(hotel. city)
        binding.txtviewProvince.setText(hotel. province)
        binding.txtviewPhone.setText(hotel. phone)
        Glide
            .with( itemView.context)
            .load(hotel. image)
            .centerCrop()
            .into( binding.ivHotel)
        setOnClickListener(position, hotel)
    }
    private fun setOnClickListener(position : Int, hotel: Hotel) {
        binding.btnEdit.setOnClickListener {
            updateOnClick(position )
        }
        binding.btnDelete.setOnClickListener {
            deleteOnClick(position)
        }
        binding.btnInfo.setOnClickListener {
            onInfoClickListener(hotel)
        }
    }
}
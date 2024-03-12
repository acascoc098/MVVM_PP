package com.example.proyectopersonalizado.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectopersonalizado.databinding.ItemBarBinding
import com.example.proyectopersonalizado.models.Bar


class ViewHBar(
    private val binding: ItemBarBinding,
    private val deleteOnClick: (Int) -> Unit,
    private val updateOnClick: (Int) -> Unit,
    private val onInfoClickListener: (Bar) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun renderize(bar : Bar, position: Int){
        /*binding.txtviewName.setText(bar. name)
        binding.txtviewCity.setText(bar. city)
        binding.txtviewProvince.setText(bar. province)
        binding.txtviewPhone.setText(bar. phone)
        Glide
            .with( itemView.context)
            .load(bar. image)
            .centerCrop()
            .into( binding.ivHotel)
        setOnClickListener(position, bar)*/
    }
    private fun setOnClickListener(position : Int, bar: Bar) {
        binding.btnEdit.setOnClickListener {
            updateOnClick(position )
        }
        binding.btnDelete.setOnClickListener {
            deleteOnClick(position)
        }
        binding.btnInfo.setOnClickListener {
            onInfoClickListener(bar)
        }
    }
}
package com.example.proyectopersonalizado.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.databinding.ItemBarBinding
import com.example.proyectopersonalizado.models.Bar

class AdapterBar(
    private var listBar: List<Bar>,
    private val deleteOnClick: (Int) -> Unit,
    private val updateOnClick: (Int) -> Unit,
    private val onInfoClickListener: (Bar) -> Unit
) : RecyclerView.Adapter<ViewHBar>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHBar {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemBarBinding.inflate(layoutInflater, parent, false)
        return ViewHBar(itemBinding, deleteOnClick, updateOnClick, onInfoClickListener)
    }

    override fun onBindViewHolder(holder: ViewHBar, position: Int) {
        holder.renderize(listBar[position], position)
    }

    override fun getItemCount(): Int = listBar.size

    fun updateData(newList: List<Bar>) {
        listBar = newList
        notifyDataSetChanged()
    }





}

package com.example.proyectopersonalizado.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.FragmentListDirections
import com.example.proyectopersonalizado.dialogues.DialogAñadirHotel
import com.example.proyectopersonalizado.dialogues.DialogBorrarHotel
import com.example.proyectopersonalizado.dialogues.DialogEditarHotel
import com.example.proyectopersonalizado.models.Hotel
import com.example.proyectopersonalizado.objets_models.Repository
import com.example.proyectopersonalizado.ui.AdapterHotel

class HotelViewModel : ViewModel() {
    private val listHotels: MutableLiveData<List<Hotel>> = MutableLiveData()

    init {
        initData()
    }

    private fun initData() {
        listHotels.value = Repository.listHotels.toMutableList()
    }

    fun getListHotels(): LiveData<List<Hotel>> {
        return listHotels
    }

    private fun updateHotelConfirm(pos: Int, recyclerView: RecyclerView) {
        recyclerView.adapter?.notifyItemChanged(pos)
    }

    fun addHotelConfirm(pos: Int, recyclerView: RecyclerView) {
        recyclerView.adapter?.notifyItemInserted(pos)
        recyclerView.smoothScrollToPosition(pos)
    }
    fun setAdapter(recyclerView: RecyclerView) {
        recyclerView.adapter = AdapterHotel(
            listHotels.value as MutableList<Hotel>,
            { pos -> delHotel(pos, recyclerView) },
            { pos -> updateHotel(pos, recyclerView) },
            { hotel -> infoHotel(hotel, recyclerView)}
        )
    }

    fun setAddButton(addButton: ImageButton, recyclerView: RecyclerView) {
        addButton.setOnClickListener {
            addHotel(recyclerView)
        }
    }
    fun delHotel(pos: Int, recyclerView: RecyclerView) {
        val alertDialogHelper = DialogBorrarHotel(recyclerView.context)
        alertDialogHelper.showConfirmationDialog(pos, listHotels.value as MutableList<Hotel>, listHotels.value!![pos].name, recyclerView)
    }

    fun updateHotel(pos: Int, recyclerView: RecyclerView) {
        val dialog = DialogEditarHotel(recyclerView.context)
        val alertDialog = dialog.showConfirmationDialog(pos, listHotels.value as MutableList<Hotel>, recyclerView, ::updateHotelConfirm)
        alertDialog?.show()
    }

    fun addHotel(recyclerView: RecyclerView) {
        val dialog = DialogAñadirHotel(recyclerView.context)
        val alertdialog = dialog.onCreateDialog(listHotels.value as MutableList<Hotel>, recyclerView, ::addHotelConfirm)
        alertdialog.show()
    }

    fun infoHotel(hotel: Hotel, recyclerView: RecyclerView) {
        val navController = recyclerView.findNavController()
        val action = FragmentListDirections.actionFragmentListToFragmentInformacion(imagen = hotel.image, nombre = hotel.name, telefono = hotel.phone, ciudad = hotel.city, provincia = hotel.province)
        navController.navigate(action)
    }



}


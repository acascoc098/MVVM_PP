package com.example.proyectopersonalizado.ui.viewmodel

import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.ui.fragments.FragmentListDirections
import com.example.proyectopersonalizado.dialogues.DialogAñadirBar
import com.example.proyectopersonalizado.dialogues.DialogBorrarBar
import com.example.proyectopersonalizado.dialogues.DialogEditarBar
import com.example.proyectopersonalizado.models.Bar
import com.example.proyectopersonalizado.objets_models.Repository
import com.example.proyectopersonalizado.adapter.AdapterBar

class BarViewModel : ViewModel() {
    private val listHotels: MutableLiveData<List<Bar>> = MutableLiveData()

    init {
        initData()
    }

    private fun initData() {
        listHotels.value = Repository.listBars.toMutableList()
    }

    fun getListHotels(): LiveData<List<Bar>> {
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
        recyclerView.adapter = AdapterBar(
            listHotels.value as MutableList<Bar>,
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
        val alertDialogHelper = DialogBorrarBar(recyclerView.context)
        alertDialogHelper.showConfirmationDialog(pos, listHotels.value as MutableList<Bar>, listHotels.value!![pos].name, recyclerView)
    }

    fun updateHotel(pos: Int, recyclerView: RecyclerView) {
        val dialog = DialogEditarBar(recyclerView.context)
        val alertDialog = dialog.showConfirmationDialog(pos, listHotels.value as MutableList<Bar>, recyclerView, ::updateHotelConfirm)
        alertDialog?.show()
    }

    fun addHotel(recyclerView: RecyclerView) {
        val dialog = DialogAñadirBar(recyclerView.context)
        val alertdialog = dialog.onCreateDialog(listHotels.value as MutableList<Bar>, recyclerView, ::addHotelConfirm)
        alertdialog.show()
    }

    fun infoHotel(bar: Bar, recyclerView: RecyclerView) {
        val navController = recyclerView.findNavController()
        val action = FragmentListDirections.actionFragmentListToFragmentInformacion(imagen = bar.image, nombre = bar.name, telefono = bar.phone, ciudad = bar.city, provincia = bar.province)
        navController.navigate(action)
    }



}


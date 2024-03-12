package com.example.proyectopersonalizado.ui.viewmodel

import android.content.Context
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.ui.fragments.FragmentListDirections
import com.example.proyectopersonalizado.dialogues.DialogAñadirBar
import com.example.proyectopersonalizado.dialogues.DialogBorrarBar
import com.example.proyectopersonalizado.dialogues.DialogEditarBar
import com.example.proyectopersonalizado.models.Bar
import com.example.proyectopersonalizado.adapter.AdapterBar
import com.example.proyectopersonalizado.data.retrofit.RetrofitModule
import com.example.proyectopersonalizado.objets_models.Preferencias
import kotlinx.coroutines.launch

class BarViewModel : ViewModel() {
    private val listBares: MutableLiveData<List<Bar>> = MutableLiveData()
    private lateinit var preferencias: Preferencias

    fun init (context: Context) {
        preferencias = Preferencias(context)
        initData()
    }

    private fun initData() {
        //listHotels.value = Repository.listBars.toMutableList()
        viewModelScope.launch {
            try {
                val token = preferencias.obtenerToken().toString()
                val response = RetrofitModule.instance.bares(token)

                if (response.isSuccessful && response.body()?.result == "ok"){
                    listBares.value = response.body()?.bares ?: emptyList()
                }
            }catch (e: Exception){
                //Error con la lista
            }
        }
    }

    fun getListHotels(): LiveData<List<Bar>> {
        return listBares
    }

    private fun updateHotelConfirm(pos: Int, recyclerView: RecyclerView) {
        recyclerView.adapter?.notifyItemChanged(pos)
    }

    fun addHotelConfirm(pos: Int, recyclerView: RecyclerView) {
        recyclerView.adapter?.notifyItemInserted(pos)
        recyclerView.smoothScrollToPosition(pos)
    }
    /*fun setAdapter(recyclerView: RecyclerView) {
        recyclerView.adapter = AdapterBar(
            listBares.value as MutableList<Bar>,
            { pos -> delHotel(pos, recyclerView) },
            { pos -> updateHotel(pos, recyclerView) },
            { hotel -> infoHotel(hotel, recyclerView)}
        )
    }*/

    fun setAddButton(addButton: ImageButton, recyclerView: RecyclerView, viewModel: BarViewModel) {
        addButton.setOnClickListener {
            addHotel(recyclerView,viewModel)
        }
    }
    fun delHotel(pos: Int, recyclerView: RecyclerView, viewModel: BarViewModel) {
        val barID = listBares.value?.get(pos)?.id ?: ""
        val alertDialogHelper = DialogBorrarBar(recyclerView.context,barID,viewModel)
        alertDialogHelper.showConfirmationDialog(pos, listBares.value as MutableList<Bar>, listBares.value!![pos].nombre, recyclerView)
    }

    fun updateHotel(pos: Int, recyclerView: RecyclerView, viewModel: BarViewModel) {
        val barID = listBares.value?.get(pos)?.id ?: ""
        val dialog = DialogEditarBar(recyclerView.context, barID, viewModel)
        val alertDialog = dialog.showConfirmationDialog(pos, listBares.value as MutableList<Bar>, recyclerView, ::updateHotelConfirm)
        alertDialog?.show()
    }

    fun addHotel(recyclerView: RecyclerView, viewModel: BarViewModel) {
        val dialog = DialogAñadirBar(recyclerView.context, viewModel)
        val alertdialog = dialog.onCreateDialog(listBares.value as MutableList<Bar>, recyclerView, ::addHotelConfirm)
        alertdialog.show()
    }

    fun infoHotel(bar: Bar, recyclerView: RecyclerView) {
        val navController = recyclerView.findNavController()
        val action = FragmentListDirections.actionFragmentListToFragmentInformacion(imagen = bar.imagen, nombre = bar.nombre, descripcion = bar.descripcion)
        navController.navigate(action)
    }



}


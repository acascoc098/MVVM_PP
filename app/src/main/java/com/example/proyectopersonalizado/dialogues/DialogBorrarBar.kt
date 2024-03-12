package com.example.proyectopersonalizado.dialogues

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.data.retrofit.RetrofitModule
import com.example.proyectopersonalizado.models.Bar
import com.example.proyectopersonalizado.objets_models.Preferencias
import com.example.proyectopersonalizado.ui.viewmodel.BarViewModel
import kotlinx.coroutines.launch

class DialogBorrarBar(val context: Context, val barID: String, val viewModel: BarViewModel) {

    private lateinit var preferencias: Preferencias
    private val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

    fun showConfirmationDialog(pos: Int, listBars: MutableList<Bar>, name: String, recyclerView: RecyclerView) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Confirmar eliminación")
        alertDialogBuilder.setMessage("¿Estás seguro de que quieres eliminar el bar "+name+"?")
        alertDialogBuilder.setPositiveButton("Sí") { dialog, _ ->
            viewModel.viewModelScope.launch {
                try {
                    val token = preferencias.obtenerToken().toString()
                    val response = RetrofitModule.instance.deleteBar(token,barID)

                    if (response.isSuccessful && response.body()?.result == "ok"){
                        //toast borramos el bar
                        listBars.removeAt(pos)

                        recyclerView.adapter?.notifyItemRemoved(pos)
                        recyclerView.adapter?.notifyItemRangeChanged(pos, listBars.size)
                    } else {
                        //error viewmodel
                    }
                }catch (e: Exception){}
            }
            /*Toast.makeText(context, "Borraremos el bar de posición $pos", Toast.LENGTH_LONG).show()
            listBars.removeAt(pos)
            recyclerView.adapter?.notifyItemRemoved(pos)
            recyclerView.adapter?.notifyItemRangeChanged(pos, listBars.size)
            dialog.dismiss()*/
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

}
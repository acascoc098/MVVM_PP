package com.example.proyectopersonalizado.dialogues

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.data.retrofit.RequestEditBar
import com.example.proyectopersonalizado.data.retrofit.RetrofitModule
import com.example.proyectopersonalizado.models.Bar
import com.example.proyectopersonalizado.objets_models.Preferencias
import com.example.proyectopersonalizado.ui.viewmodel.BarViewModel
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction2

class DialogEditarBar(val context: Context, val barID: String, val viewModel: BarViewModel) {

    private lateinit var preferencias: Preferencias
    private val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

    fun showConfirmationDialog(pos: Int, listBars: MutableList<Bar>, recyclerView: RecyclerView, updateHotelConfirm: KFunction2<Int, RecyclerView, Unit>): AlertDialog? {
        val builder = AlertDialog.Builder(context)

        // Inflar el layout para el dialog
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialogoeditar, null)

        // Obtener referencias a los campos de edición
        val etNombre = view.findViewById<EditText>(R.id.etDialog1)
        val etDescripcion = view.findViewById<EditText>(R.id.etDialog2)
        val etImagen = view.findViewById<EditText>(R.id.etDialog5)
        // ...

        // Obtener el item a editar
        val hotel = listBars[pos]

        // Establecer los valores en los campos de edición
        etNombre.setText(hotel.nombre)
        etDescripcion.setText(hotel.descripcion)
        etImagen.setText(hotel.imagen)
        // ...

        // Incorpora otras configuraciones y personalizaciones a tu diálogo
        builder.setView(view)
            .setPositiveButton("Editar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val textoNombre = view.findViewById<EditText>(R.id.etDialog1)
                    val textoDescripcion = view.findViewById<EditText>(R.id.etDialog2)
                    val textoUrl = view.findViewById<EditText>(R.id.etDialog5)

                    val nombre = textoNombre.text.toString()
                    val descripcion = textoDescripcion.text.toString()
                    val url = textoUrl.text.toString()
                    if (nombre.isNotEmpty() && descripcion.isNotEmpty() && url.isNotEmpty()){

                        viewModel.viewModelScope.launch {
                            try {
                                val token = preferencias.obtenerToken().toString()
                                val response = RetrofitModule.instance.editBar(token, barID, RequestEditBar( nombre,descripcion,url))

                                if (response.isSuccessful && response.body()?.result == "ok actualizacion"){
                                    //toast edicion correcta
                                    val nuevoBar = Bar(barID,nombre,descripcion,url);
                                    listBars.removeAt(pos)
                                    listBars.add(pos,nuevoBar)
                                    updateHotelConfirm(pos,recyclerView)
                                }

                            } catch (e: Exception){
                                //
                            }
                        }
                        /*Toast.makeText(context, "Bar editado correctamente", Toast.LENGTH_SHORT).show()
                        val nuevoBar = Bar(nombre, descripcion, url)
                        listBars.removeAt(pos)
                        listBars.add(pos, nuevoBar)
                        updateHotelConfirm(pos, recyclerView)*/
                    }else{
                        Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            .setNegativeButton("Cancelar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    // Cancela la acción y cierra el diálogo
                    dialog?.cancel()
                }
            })

            .setNegativeButton("Cancelar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    // Cancela la acción y cierra el diálogo
                    dialog?.cancel()
                }
            })
        return builder.create()
    }

}
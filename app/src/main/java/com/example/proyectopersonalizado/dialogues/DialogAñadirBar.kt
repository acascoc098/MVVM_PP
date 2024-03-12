package com.example.proyectopersonalizado.dialogues

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.data.retrofit.RequestAddBar
import com.example.proyectopersonalizado.data.retrofit.RetrofitModule
import com.example.proyectopersonalizado.models.Bar
import com.example.proyectopersonalizado.objets_models.Preferencias
import com.example.proyectopersonalizado.ui.viewmodel.BarViewModel
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction2

class DialogAñadirBar(private val context: Context, val viewModel: BarViewModel) : DialogFragment() {
    private lateinit var preferencias: Preferencias
    private lateinit var barID: String

     @SuppressLint("UseGetLayoutInflater")
     fun onCreateDialog(listBars: MutableList<Bar>, recyclerView: RecyclerView, addHotelConfirm: KFunction2<Int, RecyclerView, Unit>): AlertDialog {
        val builder = AlertDialog.Builder(context)
         preferencias = Preferencias(context)

        // Inflar el layout para el dialog
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialogoaniadir, null)

        // Incorpora otras configuraciones y personalizaciones a tu diálogo
        builder.setView(view)
            .setPositiveButton("Guardar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    val textoNombre = view.findViewById<EditText>(R.id.etDialog1)
                    val textoDescripcion = view.findViewById<EditText>(R.id.etDialog2)
                    val textoUrl = view.findViewById<EditText>(R.id.etDialog5)

                    val nombre = textoNombre.text.toString()
                    val descripcion = textoDescripcion.text.toString()
                    val url = textoUrl.text.toString()
                    if (nombre.isNotEmpty() && descripcion.isNotEmpty() && url.isNotEmpty()){
                        viewModel.viewModelScope.launch{
                            try {
                                val token = preferencias.obtenerToken().toString()
                                val response = RetrofitModule.instance.addBar(
                                    token,
                                    RequestAddBar(nombre, descripcion, url)
                                )

                                if (response.isSuccessful && response.body()?.result == "ok insercion") {
                                    barID = response.body()?.insert_id.toString()
                                    //toast añadido correctamente
                                    val bar = Bar(barID, nombre, descripcion, url)
                                    listBars.add(bar)
                                    val newPos = (barID.toInt())

                                    addHotelConfirm(newPos, recyclerView)
                                } else {
                                    //error viewmodel
                                }
                            } catch (e: Exception){
                                //error insercion
                            }
                        }
                        /*Toast.makeText(context, "Bar agregado correctamente", Toast.LENGTH_SHORT).show()
                        val bar = Bar(nombre, descripcion, url)
                        listBars.add(bar)
                        val newPos = (listBars.size-1)

                        addHotelConfirm(newPos, recyclerView)*/
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
         return builder.create()
    }

}

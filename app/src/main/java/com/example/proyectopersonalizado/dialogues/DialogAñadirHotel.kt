package com.example.proyectopersonalizado.dialogues

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.models.Hotel
import kotlin.reflect.KFunction2

class DialogAñadirHotel(private val context: Context) : DialogFragment() {

     @SuppressLint("UseGetLayoutInflater")
     fun onCreateDialog(listHotels: MutableList<Hotel>, recyclerView: RecyclerView, addHotelConfirm: KFunction2<Int, RecyclerView, Unit>): AlertDialog {
        val builder = AlertDialog.Builder(context)

        // Inflar el layout para el dialog
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialogoaniadir, null)

        // Incorpora otras configuraciones y personalizaciones a tu diálogo
        builder.setView(view)
            .setPositiveButton("Guardar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    val textoNombre = view.findViewById<EditText>(R.id.etDialog1)
                    val textoCiudad = view.findViewById<EditText>(R.id.etDialog2)
                    val textoProvincia = view.findViewById<EditText>(R.id.etDialog3)
                    val textoTelefono = view.findViewById<EditText>(R.id.etDialog4)
                    val textoUrl = view.findViewById<EditText>(R.id.etDialog5)

                    val nombre = textoNombre.text.toString()
                    val ciudad = textoCiudad.text.toString()
                    val provincia = textoProvincia.text.toString()
                    val telefono = textoTelefono.text.toString()
                    val url = textoUrl.text.toString()
                    if (nombre.isNotEmpty() && ciudad.isNotEmpty() && provincia.isNotEmpty() && telefono.isNotEmpty() && url.isNotEmpty()){
                        Toast.makeText(context, "Bar agregado correctamente", Toast.LENGTH_SHORT).show()
                        val hotel = Hotel(nombre, ciudad, provincia, telefono, url)
                        listHotels.add(hotel)
                        val newPos = (listHotels.size-1)

                        addHotelConfirm(newPos, recyclerView)
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

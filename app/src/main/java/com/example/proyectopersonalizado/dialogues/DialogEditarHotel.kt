package com.example.proyectopersonalizado.dialogues

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.models.Hotel
import kotlin.reflect.KFunction2

class DialogEditarHotel(val context: Context) {

    private val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

    fun showConfirmationDialog(pos: Int, listHotels: MutableList<Hotel>, recyclerView: RecyclerView, updateHotelConfirm: KFunction2<Int, RecyclerView, Unit>): AlertDialog? {
        val builder = AlertDialog.Builder(context)

        // Inflar el layout para el dialog
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialogoeditar, null)

        // Obtener referencias a los campos de edición
        val etNombre = view.findViewById<EditText>(R.id.etDialog1)
        val etCiudad = view.findViewById<EditText>(R.id.etDialog2)
        val etProvincia = view.findViewById<EditText>(R.id.etDialog3)
        val etNumero = view.findViewById<EditText>(R.id.etDialog4)
        val etImagen = view.findViewById<EditText>(R.id.etDialog5)
        // ...

        // Obtener el item a editar
        val hotel = listHotels[pos]

        // Establecer los valores en los campos de edición
        etNombre.setText(hotel.name)
        etCiudad.setText(hotel.city)
        etProvincia.setText(hotel.province)
        etNumero.setText(hotel.phone)
        etImagen.setText(hotel.image)
        // ...

        // Incorpora otras configuraciones y personalizaciones a tu diálogo
        builder.setView(view)
            .setPositiveButton("Editar", object : DialogInterface.OnClickListener {
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
                        Toast.makeText(context, "Bar editado correctamente", Toast.LENGTH_SHORT).show()
                        val nuevoHotel = Hotel(nombre, ciudad, provincia, telefono, url)
                        listHotels.removeAt(pos)
                        listHotels.add(pos, nuevoHotel)
                        updateHotelConfirm(pos, recyclerView)
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
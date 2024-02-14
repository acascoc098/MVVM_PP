package com.example.proyectopersonalizado

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class FragmentInformacion : Fragment() {

    private lateinit var imageViewHotel: ImageView
    private lateinit var textViewNombre: TextView
    private lateinit var textViewTelefono: TextView
    private lateinit var textViewCiudad: TextView
    private lateinit var textViewProvincia: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_informacion, container, false)

        // Inicializar las vistas
        imageViewHotel = rootView.findViewById(R.id.imahotel)
        textViewNombre = rootView.findViewById(R.id.texthotel1)
        textViewCiudad = rootView.findViewById(R.id.texthotel2)
        textViewProvincia = rootView.findViewById(R.id.texthotel3)
        textViewTelefono = rootView.findViewById(R.id.texthotel4)

        // Obtener datos de los argumentos
        val imagen = arguments?.getString("imagen")
        val nombre = arguments?.getString("nombre")
        val ciudad = arguments?.getString("ciudad")
        val provincia = arguments?.getString("provincia")
        val telefono = arguments?.getString("telefono")

        // Mostrar datos en las vistas
        Glide
            .with(this)
            .load(imagen)
            .centerCrop()
            .into(imageViewHotel)

        textViewNombre.text = nombre
        textViewCiudad.text = ciudad
        textViewProvincia.text = provincia
        textViewTelefono.text = telefono

        return rootView
    }
}

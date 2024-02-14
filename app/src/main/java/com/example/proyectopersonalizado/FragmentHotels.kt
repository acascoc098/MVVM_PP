package com.example.proyectopersonalizado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class FragmentHotels : Fragment() {
    private lateinit var texto1: TextView
    private lateinit var texto2: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotels, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        texto1 = view.findViewById(R.id.tvtextore1)
        texto2 = view.findViewById(R.id.tvtextore2)
        val myArgument : FragmentHotelsArgs by navArgs()
        val nombre = myArgument.nombre
        val curso = myArgument.curso
        texto1.setText("$nombre")
        texto2.setText("$curso")
    }

}
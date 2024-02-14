package com.example.proyectopersonalizado


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.databinding.FragmentListBinding
import com.example.proyectopersonalizado.ui.AdapterHotel
import com.example.proyectopersonalizado.viewmodel.HotelViewModel

class FragmentList : Fragment() {
    private val hotelViewModel: HotelViewModel by viewModels()
    private lateinit var enlace: FragmentListBinding
    private lateinit var botonFlotante: ImageButton
    private lateinit var myRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enlace = FragmentListBinding.inflate(inflater, container, false)
        val rootView = enlace.root
        botonFlotante = rootView.findViewById(R.id.btn_add)
        myRecyclerView = enlace.myRecyclerView // Asigna el RecyclerView

        init()
        return rootView
    }

    private fun init() {
        initRecyclerView()

        hotelViewModel.getListHotels().observe(viewLifecycleOwner, { hotels ->
            (myRecyclerView.adapter as? AdapterHotel)?.updateData(hotels)
        })

        hotelViewModel.setAdapter(myRecyclerView) // Pasa la referencia del RecyclerView
        hotelViewModel.setAddButton(botonFlotante, myRecyclerView)
    }

    private fun initRecyclerView() {
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }




}



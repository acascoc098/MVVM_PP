package com.example.proyectopersonalizado.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.databinding.FragmentListBinding
import com.example.proyectopersonalizado.adapter.AdapterBar
import com.example.proyectopersonalizado.ui.viewmodel.BarViewModel

class FragmentList : Fragment() {
    private val barViewModel: BarViewModel by viewModels()
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
        barViewModel.init(requireContext())

        initVM()
        return rootView
    }

    private fun initVM() {
        initRecyclerView()

        barViewModel.getListHotels().observe(viewLifecycleOwner, { hotels ->
            (myRecyclerView.adapter as? AdapterBar)?.updateData(hotels)
        })

        //barViewModel.setAdapter(myRecyclerView) // Pasa la referencia del RecyclerView
        barViewModel.setAddButton(botonFlotante, myRecyclerView, barViewModel)
    }

    private fun initRecyclerView() {
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }




}



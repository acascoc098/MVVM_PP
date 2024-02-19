package com.example.proyectopersonalizado.ui.view

import javax.inject.Inject

class QuePasa @Inject constructor(private val drea: Indicador) {
    fun decirme(){
        println("Andrea, dime que pasa")
    }

    fun cuenta(): String{
        return drea.cuentame()
    }
}
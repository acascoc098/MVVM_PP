package com.example.proyectopersonalizado

import javax.inject.Inject

class Prueba2Hilt @Inject constructor(private val andrea: Prueba1Hilt) {
    fun hacerAlgoCachondo() {
        println("Andrea Castilla Cocera")
    }
    fun hacerReir() : String{
        return andrea.contarChiste()
    }
}
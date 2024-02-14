package com.example.proyectopersonalizado

import javax.inject.Inject
class Prueba1Hilt @Inject constructor() {

    fun contarChiste(): String {
        return "Esto es una prueba de Hilt. "
    }
}
package com.example.proyectopersonalizado.data.retrofit

data class RequestRegistroUser (val email: String, val password: String, val nombre: String, val disponible: Int = 1)
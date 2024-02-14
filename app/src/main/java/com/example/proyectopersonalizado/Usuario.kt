package com.example.proyectopersonalizado

class Usuario {
    private lateinit var usuario: String
    private lateinit var contraseña: String

    // Getter para el atributo usuario
    fun getUsuario(): String {
        return usuario
    }

    // Setter para el atributo usuario
    fun setUsuario(usuario: String) {
        this.usuario = usuario
    }

    // Getter para el atributo contraseña
    fun getContraseña(): String {
        return contraseña
    }

    // Setter para el atributo contraseña
    fun setContraseña(contraseña: String) {
        this.contraseña = contraseña
    }
}
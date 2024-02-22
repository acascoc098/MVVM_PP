package com.example.proyectopersonalizado.objets_models

class Usuario {
    var usuario: String = ""
        set(value) {
            field = value.trim()
        }
        get() = field
    var contraseña: String = ""
        set(value) {
            field = value
        }
        get() = field

    constructor(usuario: String, contraseña: String){
        this.usuario = usuario
        this.contraseña = contraseña
    }
}
package com.example.proyectopersonalizado.models

class Bar (
    var id: String,
    var id_usuario: String,
    var nombre: String,
    var descripcion: String,
    var imagen: String
) {
    constructor(
        id: String,
        nombre: String,
        descripcion: String,
        imagen: String
    ): this(id,"",nombre,descripcion,imagen)
    override fun toString(): String {
        return "Bar(id='$id',name='$nombre', usuario='$id_usuario', descripcion='$descripcion', imagen='$imagen')"
    }
}
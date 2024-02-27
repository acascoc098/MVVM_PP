package com.example.proyectopersonalizado.objets_models

object ListaUsuario {
    private val listaUsuarios: MutableList<Usuario> = mutableListOf()

    fun agregarUsuario(usuario: Usuario) {
        listaUsuarios.add(usuario)
    }

    fun obtenerUsuarios(): List<Usuario> {
        return listaUsuarios.toList()
    }
}
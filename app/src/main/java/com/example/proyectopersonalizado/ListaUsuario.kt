package com.example.proyectopersonalizado

object ListaUsuario {
    private val usuarios: MutableList<Usuario> = mutableListOf()

    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun obtenerUsuarios(): List<Usuario> {
        return usuarios.toList()
    }

    fun eliminarUsuario(usuario: Usuario) {
        usuarios.remove(usuario)
    }
    fun comprobarUsuarios(usuario: String): Boolean {
        for (u in usuarios) {
            if (u.getUsuario() == usuario) {
                return true
            }
        }
        return false
    }
    fun comprobarUsuariosContraseña(usuario: Usuario): Boolean {
        for (u in usuarios) {
            if (usuario.getUsuario() == u.getUsuario() && usuario.getContraseña() == u.getContraseña() ) {
                return true
            }
        }
        return false
    }
}
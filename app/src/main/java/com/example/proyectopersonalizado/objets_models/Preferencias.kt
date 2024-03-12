package com.example.proyectopersonalizado.objets_models

import android.content.Context
import android.content.SharedPreferences

class Preferencias (context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("PrefenciasCompartidas", Context.MODE_PRIVATE)

    fun guardarUsuario(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.putBoolean("logueado", true)
        editor.apply()
    }

    fun obtenerToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun logueado(): Boolean {
        return sharedPreferences.getBoolean("logueado", false)
    }

    fun borrarPreferencias() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}
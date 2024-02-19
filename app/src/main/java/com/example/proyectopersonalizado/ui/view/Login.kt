package com.example.proyectopersonalizado.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectopersonalizado.R

class Login : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContra: EditText
    private lateinit var btnValidar: Button
    private lateinit var tvError: TextView
    private lateinit var btnRegistro: Button
    private lateinit var usuarioClase: Usuario
    val nombread = "admin"
    val contral = "admin"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = intent
        usuarioClase = Usuario()
        etUsuario = findViewById(R.id.etUsuario)
        etContra = findViewById(R.id.etContra)
        btnValidar = findViewById(R.id.btnValidar)
        tvError = findViewById(R.id.tvError)
        btnRegistro = findViewById(R.id.btnRegistrar)
        btnValidar.setOnClickListener {
            val MYUSER = etUsuario.text.toString()
            val MYPASS = etContra.text.toString()
            usuarioClase.setUsuario(MYUSER)
            usuarioClase.setContraseña(MYPASS)
            ListaUsuario.comprobarUsuariosContraseña(usuarioClase)
            if (ListaUsuario.comprobarUsuariosContraseña(usuarioClase) == true || usuarioClase.getUsuario() == nombread && usuarioClase.getContraseña() == contral) {
                val intent = Intent(this@Login, MainActivity::class.java)
                intent.putExtra("usuario", MYUSER)
                startActivity(intent)
            } else {
                tvError.visibility = View.VISIBLE
                tvError.text = "Usuario o contraseña incorrectos"
            }
        }
        btnRegistro.setOnClickListener{
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
}
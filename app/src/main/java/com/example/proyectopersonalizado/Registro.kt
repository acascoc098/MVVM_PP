package com.example.proyectopersonalizado

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Registro : AppCompatActivity() {

    private lateinit var usuarioEditText: EditText
    private lateinit var contraseñaEditText: EditText
    private lateinit var guardarButton: Button
    private var listaUsuarios = ListaUsuario
    private lateinit var usuarioCreado: Usuario
    private lateinit var creadoButton: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Obtener referencias de los elementos del layout
        usuarioEditText = findViewById(R.id.etUsuarioReg)
        contraseñaEditText = findViewById(R.id.etContraseñaReg)
        guardarButton = findViewById(R.id.btnValidarReg)
        creadoButton = findViewById(R.id.btnCreadoReg)

        //val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //spinner.setAdapter(adapter)

        // Crear la lista de usuarios
        listaUsuarios = ListaUsuario
        usuarioCreado = Usuario()
        creadoButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        guardarButton.setOnClickListener {

            usuarioCreado.setUsuario(usuarioEditText.text.toString())
            usuarioCreado.setContraseña(contraseñaEditText.text.toString())
            // Verificar que los campos no estén vacíos
            if (usuarioCreado.getUsuario().isEmpty() || usuarioCreado.getContraseña().isEmpty()) {
                Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Verificar si el nombre de usuario ya existe
                if (usuarioExistente(usuarioCreado.getUsuario())) {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                } else {
                    // Agregar el usuario a la lista
                    listaUsuarios.agregarUsuario(usuarioCreado)
                    Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show()

                    // Limpiar los campos de texto
                    usuarioEditText.setText("")
                    contraseñaEditText.setText("")
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun usuarioExistente(usuario: String): Boolean {
        if (listaUsuarios.comprobarUsuarios(usuario)==true){
            return true;
        }
        return false;
    }
}
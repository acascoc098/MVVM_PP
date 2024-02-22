package com.example.proyectopersonalizado.objets_models

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.data.dao.UEntityDao
import com.example.proyectopersonalizado.data.database.DBUEntity
import com.example.proyectopersonalizado.ui.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {

    /*private lateinit var etUsuario: EditText
    private lateinit var etContra: EditText
    private lateinit var btnValidar: Button
    private lateinit var tvError: TextView
    private lateinit var btnRegistro: Button
    private lateinit var usuarioClase: Usuario
    val nombread = "admin"
    val contral = "admin"*/

    private lateinit var preferencias: Preferencias
    lateinit var dao : UEntityDao
    lateinit var database : DBUEntity
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        database = DBUEntity.getDatabase(applicationContext);
        dao = database.usuarioEntityDao()
        preferencias = Preferencias(this)



        if(preferencias.logueado() == true){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val miBoton = findViewById<Button>(R.id.btnValidar)
        val textoClicable = findViewById<Button>(R.id.btnRegistrar)

        textoClicable.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }


        miBoton.setOnClickListener {

            val editTexUsuario = findViewById<EditText>(R.id.etUsuario)
            val valorUsuario: String = editTexUsuario.text.toString()

            val editTextPass = findViewById<EditText>(R.id.etContra)
            val valorPass: String = editTextPass.text.toString()



            GlobalScope.launch(Dispatchers.IO) {
                val comprobarUser = dao.getUsuarioByUsuarioAndPassword(valorUsuario, valorPass)
                if(comprobarUser!=null){
                    preferencias.guardarUsuario(valorUsuario);
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Login, "USUARIO O CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}
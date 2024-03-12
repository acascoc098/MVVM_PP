package com.example.proyectopersonalizado.objets_models

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.data.retrofit.RequestRegistroUser
import com.example.proyectopersonalizado.data.retrofit.RetrofitModule
import com.example.proyectopersonalizado.data.room.dao.UEntityDao
import com.example.proyectopersonalizado.data.room.database.DBUEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Registro : AppCompatActivity() {

    lateinit var dao : UEntityDao
    lateinit var database : DBUEntity

    private lateinit var imagViewFoto: ImageView
    private lateinit var btnCapturaFoto: Button
    private lateinit var btnGuardarFoto: Button
    private lateinit var btnGaleria: Button
    private var bitmap: Bitmap? = null

    private lateinit var inicioActividadCamara: ActivityResultLauncher<Intent>
    private lateinit var inicioActividadLecturaGaleria: ActivityResultLauncher<Intent>

    private val RESPUESTA_PERMISO_CAMARA = 100
    private val RESPUESTA_PERMISO_ALMACENAMIENTO = 200
    private val RESPUESTA_PERMISO_GALERIA = 300
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val BotonRegistrarse = findViewById<Button>(R.id.btnValidarReg)
        val BotonVolver = findViewById<Button>(R.id.btnCreadoReg)
        database = DBUEntity.getDatabase(applicationContext);
        dao = database.usuarioEntityDao()

        imagViewFoto = findViewById(R.id.imgUsu)
        btnCapturaFoto = findViewById(R.id.btnCamara)
        btnGaleria = findViewById(R.id.btnCargar)
        btnGuardarFoto = findViewById(R.id.btnGuardar)

        btnCapturaFoto.setOnClickListener {
            if (compruebaPermisosCamara()) tomarFotoCamara()
        }

        btnGuardarFoto.setOnClickListener {
            if (compruebaPermisosAlmacenamiento()) almacenarFotoEnGaleria()
        }

        btnGaleria.setOnClickListener {
            if (compruebaPermisosLecturaGaleria()) cargarDesdeGaleria()
        }

        crearInicioActividadCamara()
        crearInicioActividadLeerImagenGaleria()

        BotonVolver.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


        BotonRegistrarse.setOnClickListener {
            val editNuevoUsuario = findViewById<EditText>(R.id.etUsuarioReg)
            val nuevoUsuario = editNuevoUsuario.text.toString()

            val editsetNuevaContraseña = findViewById<EditText>(R.id.etContraseñaReg)
            val nuevaContraseña = editsetNuevaContraseña.text.toString()

            val editNombre = findViewById<EditText>(R.id.etNombreReg)
            val nuevoNombre = editNombre.text.toString()

            //val nuevoUser = UsuarioEntity (usuario = nuevoUsuario, contrasena = nuevaContraseña)

            /*GlobalScope.launch {
                val existingUser = dao.getUsuarioByUsuario(nuevoUsuario)
                if (existingUser == null) {
                    dao.insertarUsuario(nuevoUser)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Registro, "REGISTRADO SATISFACTORIAMENTE", Toast.LENGTH_LONG).show()
                        delay(2000)
                        finish() // Cierra la actividad actual
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Registro, "EMAIL YA REGISTRADO", Toast.LENGTH_LONG).show()
                    }
                }
            }*/

            GlobalScope.launch(Dispatchers.IO){
                val response = withContext(Dispatchers.IO){
                    RetrofitModule.instance.registro(RequestRegistroUser( nuevoUsuario,nuevaContraseña,nuevoNombre))
                }

                if (response.isSuccessful && response.body()?.result == "ok"){
                    if (response.body()?.insert_id == 0){
                        withContext(Dispatchers.Main){
                            //Ya registrado
                        }
                    } else {
                        withContext(Dispatchers.Main){
                            //Resgitrado
                        }
                        val intent = Intent(this@Registro, Login::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main){
                        //No se hace el registro
                    }
                }
            }



        }
    }


    private fun almacenarFotoEnGaleria() {
        // Implementación del método almacenarFotoEnGaleria...
    }

    private fun compruebaPermisosCamara(): Boolean {
        // Implementación del método compruebaPermisosCamara...
    }

    private fun compruebaPermisosLecturaGaleria(): Boolean {
        // Implementación del método compruebaPermisosLecturaGaleria...
    }

    private fun compruebaPermisosAlmacenamiento(): Boolean {
        // Implementación del método compruebaPermisosAlmacenamiento...
    }

    private fun crearInicioActividadCamara() {
        inicioActividadCamara = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                bitmap = result.data?.extras?.get("data") as? Bitmap
                imagViewFoto.setImageBitmap(bitmap)
            }
        }
    }

    private fun crearInicioActividadLeerImagenGaleria() {
        inicioActividadLecturaGaleria = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imagenUri: Uri? = result.data?.data
                imagViewFoto.setImageURI(imagenUri)
            }
        }
    }

    private fun tomarFotoCamara() {
        val intentCamara = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        inicioActividadCamara.launch(intentCamara)
    }

    private fun cargarDesdeGaleria() {
        val intentGaleria = Intent(Intent.ACTION_GET_CONTENT)
        intentGaleria.type = "image/*"
        inicioActividadLecturaGaleria.launch(intentGaleria)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        // Implementación del método onRequestPermissionsResult...
    }




    /*private lateinit var usuarioEditText: EditText
    private lateinit var contraseñaEditText: EditText
    private lateinit var guardarButton: Button
    private var listaUsuarios = ListaUsuario
    private lateinit var usuarioCreado: Usuario
    private lateinit var creadoButton: Button*/


    /*override fun onCreate(savedInstanceState: Bundle?) {
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
                    ListaUsuario.agregarUsuario(usuarioCreado)
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
        if (ListaUsuario.comprobarUsuarios(usuario) ==true){
            return true;
        }
        return false;
    }*/
}
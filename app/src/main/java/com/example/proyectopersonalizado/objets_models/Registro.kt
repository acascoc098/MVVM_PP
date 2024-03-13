package com.example.proyectopersonalizado.objets_models

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.data.retrofit.RequestRegistroUser
import com.example.proyectopersonalizado.data.retrofit.RetrofitModule
import com.example.proyectopersonalizado.data.room.dao.UEntityDao
import com.example.proyectopersonalizado.data.room.database.DBUEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


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
        imagViewFoto.setImageResource(R.drawable.usuario)
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
                            Toast.makeText(
                                this@Registro,
                                "EMAIL YA REGISTRADO",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        withContext(Dispatchers.Main){
                            Toast.makeText(
                                this@Registro,
                                "REGISTRADO SATISFACTORIAMENTE",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        val intent = Intent(this@Registro, Login::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(
                            this@Registro,
                            "NO SE PUDO REALIZAR EL REGISTRO",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }



        }
    }


    private fun almacenarFotoEnGaleria() {
        var fos: OutputStream? = null
        var f: File? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            val resolver: ContentResolver = contentResolver

            val fileName = "${System.currentTimeMillis()}imagen_de_ejemplo"
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.IS_PENDING, 1)

            val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val imageUri: Uri? = resolver.insert(collection, values)

            try {
                fos = imageUri?.let { resolver.openOutputStream(it) }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            imageUri?.let { resolver.update(it, values, null, null) }
        } else {
            val imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
            val fileName = "${System.currentTimeMillis()}.jpg"

            f = File(imageDir, fileName)

            try {
                fos = FileOutputStream(f)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        val salvado = fos?.let { bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it) } ?: false
        if (salvado)
            Toast.makeText(this, "La imagen se guardó satisfactoriamente", Toast.LENGTH_SHORT).show()

        fos?.let {
            try {
                it.flush()
                it.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        f?.let {
            MediaScannerConnection.scanFile(this, arrayOf(it.toString()), null, null)
        }
    }

    private fun compruebaPermisosCamara(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    RESPUESTA_PERMISO_CAMARA
                )
                false
            }
        } else {
            true
        }
    }

    private fun compruebaPermisosAlmacenamiento(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED || run {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                RESPUESTA_PERMISO_ALMACENAMIENTO
            )
            false
        }
    }


    private fun compruebaPermisosLecturaGaleria(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED || run {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RESPUESTA_PERMISO_GALERIA
            )
            false
        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            RESPUESTA_PERMISO_CAMARA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tomarFotoCamara()
                } else {
                    Toast.makeText(this, "No se han aceptado los permisos de la cámara", Toast.LENGTH_SHORT).show()
                }
            }
            RESPUESTA_PERMISO_ALMACENAMIENTO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    almacenarFotoEnGaleria()
                } else {
                    Toast.makeText(this, "No se han aceptado los permisos de escritura", Toast.LENGTH_SHORT).show()
                }
            }
            RESPUESTA_PERMISO_GALERIA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cargarDesdeGaleria()
                } else {
                    Toast.makeText(this, "No se han aceptado los permisos de lectura", Toast.LENGTH_SHORT).show()
                }
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
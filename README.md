## PROYECTO PERSONALIZADO
#### Versión final con API

**AUTORA: ANDREA CASTILLA COCERA**
`Repositorios`:

[API (api-bares)](https://github.com/acascoc098/docker-lamp-bares.git)

[PROYECTO](https://github.com/acascoc098/MVVM_PP.git)

###### ESTRUCTURA 
Para esto he creado la siguiente carpeta con esta estructura:

Ruta: app/src/main/java/com/example/proyectopersonalizado/data
* data
  * retrofit
    * ApiService -> Donde hacemos las peticiones POST, GET, PUT y DELETE
    * RequestAddBar y ResponseAddBar -> Para añadir un bar
    * RequestEditBar y ResponseEditBar -> Para la modificación de un bar
    * ResponseDelteBar -> Para eliminar un bar
    * RequestLoginUser y ResponseLogin -> Para la parte de logearse
    * RequestRegistroUser y ResponseRegistro -> Para la parte de registrarse
    * ResponseBares -> Para la obtención de la lista de bares
    * RetrofitModule -> Donde llamamos a la API

Primero vamos a ver que tenemos en `ApiService`:
```kotlin

interface ApiService {
    @POST("endp/auth")
    suspend fun auth(@Body loginUser: RequestLoginUser): Response<ResponseLogin>

    @POST("endp/registro")
    suspend fun registro(@Body registro: RequestRegistroUser): Response<ResponseResgitro>

    @GET("endp/bar")
    suspend fun bares(@Header("api-key") apikey: String) : Response<ResponseBares>

    @POST("endp/bar")
    suspend fun addBar(@Header("api-key") apikey: String, @Body bar: RequestAddBar): Response<ResponseAddBar>

    @PUT("endp/bar")
    suspend fun editBar(@Header("api-key") apikey: String, @Query("id") barId: String, @Body bar: RequestEditBar): Response<ResponseEditBar>

    @DELETE("endp/bar")
    suspend fun deleteBar(@Header("api-key") apikey: String, @Query("id") barId: String): Response<ResponseDeleteBar>
}
```
Aquí se gestionan las peticiones a la api.

Y por otro lado veamos `RetrofitModule`:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val URL_BASE_RETROFIT = "http://10.0.2.2/api-bares/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE_RETROFIT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}
```
Ahora veamos los request y response:

1. Para el login:
```kotlin
data class RequestLoginUser(

    val email : String,
    val password : String
)

data class ResponseLogin (


    val result: String,


    val token : String,
    )
```
2. Para el registro:
```kotlin
data class RequestRegistroUser (val email: String, val password: String, val nombre: String, val disponible: Int = 1)

data class ResponseResgitro(val result: String, val insert_id: Int)
```
3. Para añadir un bar:
```kotlin
data class RequestAddBar (val nombre: String, val descripcion: String, val imagen: String)

data class ResponseAddBar(val result: String, val insert_id: String, val imagen: String)
```
4. Para editar un bar:
```kotlin
data class RequestEditBar(val nombre: String, val descripcion: String, val imagen: String)

data class ResponseEditBar(val result: String, val imagen: String)
```
5. Para eliminar un bar:
```kotlin
data class ResponseDeleteBar(val result:String)
```

###### ACTIVITIES

Aquí veremos dos partes: `Login` y `Registro`.

Para el **Login** hacemos estos cambios:
```kotlin
GlobalScope.launch(Dispatchers.IO) {
                val response = RetrofitModule.instance.auth(RequestLoginUser(valorUsuario,valorPass))

                if (response.isSuccessful && response.body()?.result == "ok") {
                    preferencias.guardarUsuario(response.body()!!.token);
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Login, "EMAIL O CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG).show()
                    }
                }
            }
```

Y para el **Resgistro** hacemos estos cambios:
```kotlin
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
```
Además de añadir en el html un `EditText` para el nombre:
```html
<EditText
                android:id="@+id/etNombreReg"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginLeft="10dp"
                android:layout_marginRight="10dp"
                android:layout_marginBottom="20dp"
                android:background="@drawable/edittext"
                android:hint="   Nombre" />
```

###### DIÁLOGOS
Aquí nos econtramos cambios en los 3 diálogos:
*DialogAñadirBar*
```kotlin
val textoNombre = view.findViewById<EditText>(R.id.etDialog1)
                    val textoDescripcion = view.findViewById<EditText>(R.id.etDialog2)
                    val textoUrl = view.findViewById<EditText>(R.id.etDialog5)

                    val nombre = textoNombre.text.toString()
                    val descripcion = textoDescripcion.text.toString()
                    val url = textoUrl.text.toString()
                    if (nombre.isNotEmpty() && descripcion.isNotEmpty() && url.isNotEmpty()){
                        viewModel.viewModelScope.launch{
                            try {
                                val token = preferencias.obtenerToken().toString()
                                val response = RetrofitModule.instance.addBar(
                                    token,
                                    RequestAddBar(nombre, descripcion, url)
                                )

                                if (response.isSuccessful && response.body()?.result == "ok insercion") {
                                    barID = response.body()?.insert_id.toString()
                                    //toast añadido correctamente
                                    val bar = Bar(barID, nombre, descripcion, url)
                                    listBars.add(bar)
                                    val newPos = (barID.toInt())

                                    addHotelConfirm(newPos, recyclerView)
                                } else {
                                    //error viewmodel
                                }
                            } catch (e: Exception){
                                //error insercion
                            }
                        }
```
*DialogBorrarBar*
```kotlin
val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Confirmar eliminación")
        alertDialogBuilder.setMessage("¿Estás seguro de que quieres eliminar el bar "+name+"?")
        alertDialogBuilder.setPositiveButton("Sí") { dialog, _ ->
            viewModel.viewModelScope.launch {
                try {
                    val token = preferencias.obtenerToken().toString()
                    val response = RetrofitModule.instance.deleteBar(token,barID)

                    if (response.isSuccessful && response.body()?.result == "ok"){
                        //toast borramos el bar
                        listBars.removeAt(pos)

                        recyclerView.adapter?.notifyItemRemoved(pos)
                        recyclerView.adapter?.notifyItemRangeChanged(pos, listBars.size)
                    } else {
                        //error viewmodel
                    }
                }catch (e: Exception){}
            }
```
*DialogEditarBar*
```kotlin
val textoNombre = view.findViewById<EditText>(R.id.etDialog1)
                    val textoDescripcion = view.findViewById<EditText>(R.id.etDialog2)
                    val textoUrl = view.findViewById<EditText>(R.id.etDialog5)

                    val nombre = textoNombre.text.toString()
                    val descripcion = textoDescripcion.text.toString()
                    val url = textoUrl.text.toString()
                    if (nombre.isNotEmpty() && descripcion.isNotEmpty() && url.isNotEmpty()){

                        viewModel.viewModelScope.launch {
                            try {
                                val token = preferencias.obtenerToken().toString()
                                val response = RetrofitModule.instance.editBar(token, barID, RequestEditBar( nombre,descripcion,url))

                                if (response.isSuccessful && response.body()?.result == "ok actualizacion"){
                                    //toast edicion correcta
                                    val nuevoBar = Bar(barID,nombre,descripcion,url);
                                    listBars.removeAt(pos)
                                    listBars.add(pos,nuevoBar)
                                    updateHotelConfirm(pos,recyclerView)
                                }

                            } catch (e: Exception){
                                //
                            }
                        }
```
**NO OLNIDES LOS CAMBIOS NECESARIOS EN LOS ARCHIVOS HTML**

###### VIEWMODEL
En cuanto al viewmodel debemos hacer estor cambios:

```kotlin
class BarViewModel : ViewModel() {
    private val listBares: MutableLiveData<List<Bar>> = MutableLiveData()
    private lateinit var preferencias: Preferencias

    fun init (context: Context) {
        preferencias = Preferencias(context)
        initData()
    }

    private fun initData() {
        //listHotels.value = Repository.listBars.toMutableList()
        viewModelScope.launch {
            try {
                val token = preferencias.obtenerToken().toString()
                val response = RetrofitModule.instance.bares(token)

                if (response.isSuccessful && response.body()?.result == "ok"){
                    listBares.value = response.body()?.bares ?: emptyList()
                }
            }catch (e: Exception){
                //Error con la lista
            }
        }
    }

    fun getListHotels(): LiveData<List<Bar>> {
        return listBares
    }

    private fun updateHotelConfirm(pos: Int, recyclerView: RecyclerView) {
        recyclerView.adapter?.notifyItemChanged(pos)
    }

    fun addHotelConfirm(pos: Int, recyclerView: RecyclerView) {
        recyclerView.adapter?.notifyItemInserted(pos)
        recyclerView.smoothScrollToPosition(pos)
    }

    fun setAddButton(addButton: ImageButton, recyclerView: RecyclerView, viewModel: BarViewModel) {
        addButton.setOnClickListener {
            addHotel(recyclerView,viewModel)
        }
    }
    fun delHotel(pos: Int, recyclerView: RecyclerView, viewModel: BarViewModel) {
        val barID = listBares.value?.get(pos)?.id ?: ""
        val alertDialogHelper = DialogBorrarBar(recyclerView.context,barID,viewModel)
        alertDialogHelper.showConfirmationDialog(pos, listBares.value as MutableList<Bar>, listBares.value!![pos].nombre, recyclerView)
    }

    fun updateHotel(pos: Int, recyclerView: RecyclerView, viewModel: BarViewModel) {
        val barID = listBares.value?.get(pos)?.id ?: ""
        val dialog = DialogEditarBar(recyclerView.context, barID, viewModel)
        val alertDialog = dialog.showConfirmationDialog(pos, listBares.value as MutableList<Bar>, recyclerView, ::updateHotelConfirm)
        alertDialog?.show()
    }

    fun addHotel(recyclerView: RecyclerView, viewModel: BarViewModel) {
        val dialog = DialogAñadirBar(recyclerView.context, viewModel)
        val alertdialog = dialog.onCreateDialog(listBares.value as MutableList<Bar>, recyclerView, ::addHotelConfirm)
        alertdialog.show()
    }

    fun infoHotel(bar: Bar, recyclerView: RecyclerView) {
        val navController = recyclerView.findNavController()
        val action = FragmentListDirections.actionFragmentListToFragmentInformacion(imagen = bar.imagen, nombre = bar.nombre, descripcion = bar.descripcion)
        navController.navigate(action)
    }
}
```
Además, habrá que cambiar el modelo bar para que se asemeje a nuestra api:
```kotlin
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
```

## PROYECTO PERSONALIZADO
#### Versión final con API

[API (api-bares)](https://github.com/acascoc098/docker-lamp-bares.git)

###### ESTRUCTURA 
Para esto he creado la siguiente carpeta con esta estructura:
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

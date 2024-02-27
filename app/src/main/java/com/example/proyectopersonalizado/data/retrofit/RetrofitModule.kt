package com.example.proyectopersonalizado.data.retrofit

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    /*
   1.- Hilt creará una sóla instancia de Retrofit.
   2.- A partir de la instancia de Retrofit, devolverá una
   única instancia del Servicio.
    */

    private const val URL_BASE_RETROFIT = "http://10.0.2.2/api-pueblos/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE_RETROFIT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}
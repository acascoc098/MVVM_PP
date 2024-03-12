package com.example.proyectopersonalizado.data.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {
    @POST("endp/auth")
    suspend fun auth(@Body loginUser: RequestLoginUser): Response<ResponseLogin>

    @POST("endp/registro")
    suspend fun registro(@Body registro: RequestRegistroUser): Response<ResponseResgitro>

    @GET("endp/bar")
    suspend fun bares(@Header("api-key")) : Response
}
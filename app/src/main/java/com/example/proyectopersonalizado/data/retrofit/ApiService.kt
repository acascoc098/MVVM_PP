package com.example.proyectopersonalizado.data.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("endp/auth")
    suspend fun inserte(@Body user: RequestUser): Response<ResponseU>
}
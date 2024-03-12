package com.example.proyectopersonalizado.data.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


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
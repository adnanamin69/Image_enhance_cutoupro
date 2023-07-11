package com.example.myapplication.network

import com.example.myapplication.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Headers("APIKEY: yourKey")// todo add your api key here
    @Multipart
    @POST("api/v1/photoEnhance2")
    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<ApiResponse>
}
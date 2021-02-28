package com.example.apiexample

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
   suspend fun getDogs(@Url url:String):Response<DogResponse>
}
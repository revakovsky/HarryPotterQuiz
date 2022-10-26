package com.revakovskyi.harrypotterquiz.utils

import com.google.gson.GsonBuilder
import com.revakovskyi.harrypotterquiz.questions.model.EmptyRequest
import com.revakovskyi.harrypotterquiz.questions.model.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiInterface {

    @Headers("Accept: application/json")
    @GET(".")
    fun getRequest(): Call<EmptyRequest>

    companion object {

        var BASE_URL = "https://revamax.monster/YRQj7j/"

        fun create(): ApiInterface {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}
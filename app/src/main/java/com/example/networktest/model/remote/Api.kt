package com.example.networktest.model.remote

import com.example.networktest.model.presentation.BookResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("books/v1/volumes")

    fun getBookByTitle(
        @Query("q") bookTitle: String,
        @Query("printType")  bookType: String = "books",
        @Query("maxResults") max: Int = 5
    ): Call<BookResponse>

    companion object{
        fun initRetrofit(): Api{
            return Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}

/**
 * 1. - Adding dependencies. (Search for retrofit, converter-[gson,moshi]
 * 2. - Create the 'api' interface
 * 3. - Define the http verbs (GET, POST, PUT, DELETE)
 * 4. - create the retrofit object (using the builder)
 * 5. - Invoke the network cal (enqueue, execute)
 */
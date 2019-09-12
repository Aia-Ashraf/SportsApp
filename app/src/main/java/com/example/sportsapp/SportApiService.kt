package com.example.sportsapp

import com.example.sportsapp.models.Article
import com.example.sportsapp.models.SportsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SportApiService {
    @GET("top-headlines")
    fun getSportsList(
        @Query("country")
        country: String,@Query("category")
        category: String,@Query("apiKey")
        apiKey: String
    ): Observable<SportsModel>

    //call or Observable for Rx
}
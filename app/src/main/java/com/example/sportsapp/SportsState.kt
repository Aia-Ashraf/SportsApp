package com.example.sportsapp

import com.example.sportsapp.models.Article

sealed class SportsState {
    object LoadingState : SportsState()
    data class DataState(val data: List<Article>) : SportsState()
    data class ErrorState(val data: String) : SportsState()
}
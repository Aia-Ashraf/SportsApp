package com.example.sportsapp.models

import com.example.sportsapp.models.Article

data class SportsModel(
    var articles: List<Article>,
    val status: String,
    val totalResults: Int
)
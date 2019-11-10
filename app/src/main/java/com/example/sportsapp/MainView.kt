package com.example.sportsapp

interface MainView {
    fun showLoading(sportsState: SportsState)
    fun render(sportsState: SportsState)
}
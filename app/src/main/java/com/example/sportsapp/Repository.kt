package com.example.sportsapp

import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.sportsapp.Constants.BASE_URL
import com.example.sportsapp.models.Article
import com.example.sportsapp.models.Source
import com.example.sportsapp.models.SportsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository(var articleList: Array<Article>?) {
    companion object {
        fun create(): SportApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(SportApiService::class.java)
        }
    }

    val sportApiServe by lazy {
        Repository.create()
    }
    var disposable: Disposable? = null
    var list: SportsModel? = null


    fun beginSearch() {
        sportApiServe.getSportsList("eg", "sports", "aa101e13a76b4e259ab2cc739092edb7")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {getObservableFromString(it) },
                { this::handleError/*error -> error.cause)*/ }
            )
    }

    /*    fun handleResponse(result: SportsModel) {
            list=result
        }*/
    fun getObservableFromString(movieResponse: SportsModel) {
        list = movieResponse

        articleList = movieResponse.articles.toTypedArray()

    }

    private fun handleError(error: Throwable) {

        Log.d("aiaaaaaaaaaaaaaaaaa", error.localizedMessage)

    }

}
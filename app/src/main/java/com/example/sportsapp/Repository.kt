package com.example.sportsapp

import android.widget.TextView
import com.example.sportsapp.Constants.BASE_URL
import com.example.sportsapp.models.Article
import com.example.sportsapp.models.Source
import com.example.sportsapp.models.SportsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
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
        disposable =
            sportApiServe.getSportsList("eg", "sports", "aa101e13a76b4e259ab2cc739092edb7")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> (handleResponse(result = result)) },
                    { t: Throwable? -> t?.message /*error -> error.cause)*/ }
                )
    }

    fun handleResponse(result: SportsModel) {
        list.let { list = result }
    }
}
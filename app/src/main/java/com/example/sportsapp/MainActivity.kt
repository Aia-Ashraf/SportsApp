package com.example.sportsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.models.Article
import com.example.sportsapp.models.SportsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var repository: Repository
    var sportsModel: SportsModel? = null
    lateinit var viewLayoutManager: RecyclerView.LayoutManager
     var sportsAdapter: SportsAdapter?=null
    var articleListt: List<Article>?=null
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beginSearch()
        sportsModel?.let { getObservableFromString(it.articles) }
        if(articleListt !=null) {
            viewLayoutManager = LinearLayoutManager(this)
            recyclerView = RecyclerView(this)
            recyclerView = findViewById<RecyclerView>(R.id.rv).apply {
                setHasFixedSize(true)
                layoutManager = viewLayoutManager
                sportsAdapter = SportsAdapter(articleListt)
            }
            txt.text = articleListt.toString()
            Log.d("aia",articleListt.toString())
        }else   beginSearch()
        sportsModel?.let { getObservableFromString(it.articles) }
    }
    override fun onPause() {
        super.onPause()
        repository.disposable?.dispose()
    }


    companion object {
        fun create(): SportApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(Constants.BASE_URL)
                .build()

            return retrofit.create(SportApiService::class.java)
        }
    }

    val sportApiServe by lazy {
        Repository.create()
    }


    fun beginSearch() {
        sportApiServe.getSportsList("eg", "sports", "aa101e13a76b4e259ab2cc739092edb7")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {getObservableFromString(it.articles) },
                { this::handleError/*error -> error.cause)*/ }

            )
    }

    fun getObservableFromString(articleList: List<Article>) {
      articleListt=articleList
    }

    private fun handleError(error: Throwable) {

        Log.d("aiaaaaaaaaaaaaaaaaa", error.localizedMessage)

    }
}

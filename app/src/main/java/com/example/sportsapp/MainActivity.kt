package com.example.sportsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
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
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sportApiServe by lazy {
            Repository.create()
        }
        disposable= sportApiServe.getSportsList("eg", "sports", "aa101e13a76b4e259ab2cc739092edb7")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    articleListt=it.articles
                    sportsAdapter = SportsAdapter(articleListt)
                },
                { error -> error.cause }
            )


        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = SportsAdapter(articleListt)
        sportsAdapter?.notifyItemInserted(articleListt?.size!!)

        /*     viewLayoutManager = LinearLayoutManager(this)
             recyclerView = RecyclerView(this)
             recyclerView = findViewById<RecyclerView>(R.id.rv).apply {
                 setHasFixedSize(true)
                 viewLayoutManager=LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                 layoutManager = viewLayoutManager
                 this.adapter= sportsAdapter

                     sportsAdapter?.list=articleListt
                 sportsAdapter?.notifyItemInserted(articleListt?.size!!)

                 Log.d("aia",articleListt.toString())
             }*/


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



    var disposable: Disposable? = null

    fun beginSearch() {

    }

    fun getObservableFromString(articleList: List<Article>) {
      articleListt=articleList
    }

    private fun handleError(error: Throwable) {

        Log.d("aiaaaaaaaaaaaaaaaaa", error.localizedMessage)
    }
}
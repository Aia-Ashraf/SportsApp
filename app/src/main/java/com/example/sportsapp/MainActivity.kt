package com.example.sportsapp

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class MainActivity : AppCompatActivity(), MainView
{


    lateinit var repository: Repository
    var sportsModel: SportsModel? = null
    lateinit var viewLayoutManager: LinearLayoutManager
    lateinit var sportsAdapter: SportsAdapter
    var articleListt: List<Article>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var sportsState: SportsState
    lateinit var presenter: MainPresenter
    lateinit var view: MainView
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view.render(sportsState)
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


    private fun handleError(error: String?) {
        Log.d("MainActivity", error.toString())
    }

    override fun showLoading(sportsState: SportsState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(sportsState: SportsState) {
        when (sportsState) {
            is SportsState.DataState -> renderDataState(sportsState)
            is SportsState.LoadingState -> renderLoadingState()
            is SportsState.ErrorState -> renderErrorState(sportsState)
        }
    }

    private fun renderDataState(dataState: SportsState.DataState) {

        viewLayoutManager = LinearLayoutManager(this)
        rv.layoutManager = viewLayoutManager
        sportsAdapter = SportsAdapter(articleListt, this)
        rv.adapter = sportsAdapter
        val dividerItemDecoration = DividerItemDecoration(
            rv.context,
            viewLayoutManager?.getOrientation()
        )
        rv.addItemDecoration(dividerItemDecoration)


        val sportApiServe by lazy {
            Repository.create()
        }
        sportApiServe.getSportsList("eg", "sports", "aa101e13a76b4e259ab2cc739092edb7")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    articleListt = it.articles
                    sportsAdapter.list = articleListt
                    articleListt?.lastIndex?.let { it1 -> sportsAdapter?.notifyItemInserted(it1) }

                },
                { error -> handleError(error.message) }
            )
    }

    private fun renderLoadingState() {
    }

    private fun renderErrorState(errorState: SportsState.ErrorState) {
    }
}
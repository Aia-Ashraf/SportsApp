package com.example.sportsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.models.SportsModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var repository: Repository
    var sportsModel: SportsModel? = null
    lateinit var viewLayoutManager: RecyclerView.LayoutManager
    lateinit var sportsAdapter: SportsAdapter
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        print("doneeeeeeeeeeeeeeeeeeee\")")
        repository = Repository()
        repository.beginSearch()
        txt.text =
            sportsModel?.let { repository.getObservableFromString(movieResponse = it).toString() }
        print(repository.articleList.toString() + "(\"doneeeeeeeeeeeeeeeeeeee\")")

        viewLayoutManager = LinearLayoutManager(this)

        sportsAdapter = repository.articleList?.let { SportsAdapter(it) }!!
        recyclerView = findViewById<RecyclerView>(R.id.rv).apply {
            setHasFixedSize(true)
            layoutManager = viewLayoutManager
            recyclerView.adapter = sportsAdapter
        }
    }

    override fun onPause() {
        super.onPause()
        repository.disposable?.dispose();
    }

}

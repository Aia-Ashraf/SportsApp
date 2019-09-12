package com.example.sportsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportsapp.models.Article
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        print("doneeeeeeeeeeeeeeeeeeee\")")
        repository = Repository()
        repository.beginSearch()
        txt.text=repository.list.toString()
        print(repository.list.toString()+"(\"doneeeeeeeeeeeeeeeeeeee\")")
    }

    override fun onPause() {
        super.onPause()
        repository.disposable?.dispose();
    }
}

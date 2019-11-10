package com.example.sportsapp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainPresenter (view: MainView) {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: MainView

    fun bind(view: MainView) {
        this.view = view
//        compositeDisposable.add(observeMovieDeleteIntent())
//        compositeDisposable.add(observeSportsDisplay())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

   /* private fun observeSportsDisplay() = loadSportsList()
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.render(SportsState.LoadingState) }
        .doOnNext { view.render(it) }
        .subscribe()*/
}

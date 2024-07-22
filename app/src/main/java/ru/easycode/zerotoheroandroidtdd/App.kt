package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import android.util.Log

class App : Application() {

    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        println()
        viewModel = MainViewModel(LiveDataWrapper.Base(), Repository.Base())
        Log.d("TAG", "onCreate: Application")
    }
}
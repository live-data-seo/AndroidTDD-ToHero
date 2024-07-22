package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.easycode.zerotoheroandroidtdd.data.cloud.SimpleService
import ru.easycode.zerotoheroandroidtdd.data.repository.Repository
import ru.easycode.zerotoheroandroidtdd.ui.LiveDataWrapper

class App : Application() {

    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: SimpleService = retrofit.create(SimpleService::class.java)

        viewModel = MainViewModel(
            liveDataWrapper = LiveDataWrapper.Base(),
            repository = Repository.Base(
                service = service,
                url = URL
            )
        )
    }

    companion object {
        private const val URL =
            "https://raw.githubusercontent.com/JohnnySC/ZeroToHeroAndroidTDD/task/018-clouddatasource/app/sampleresponse.json"
    }
}
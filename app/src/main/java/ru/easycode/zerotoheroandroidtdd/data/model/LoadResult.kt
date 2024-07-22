package ru.easycode.zerotoheroandroidtdd.data.model

import ru.easycode.zerotoheroandroidtdd.data.cloud.SimpleResponse
import ru.easycode.zerotoheroandroidtdd.ui.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.ui.UiState

interface LoadResult {
    fun show(updateLiveData: LiveDataWrapper.Update)

    data class Success(private val data: SimpleResponse) : LoadResult{
        override fun show(updateLiveData: LiveDataWrapper.Update) {
            updateLiveData.update(UiState.Loaded(data.text))
        }
    }

    data class Error(private val noConnection: Boolean) : LoadResult{
        override fun show(updateLiveData: LiveDataWrapper.Update) {
            if(noConnection)
               return updateLiveData.update(UiState.Loaded("No internet connection"))
            return updateLiveData.update(UiState.Loaded("Something went wrong"))
        }
    }
}
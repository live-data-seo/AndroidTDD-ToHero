package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(
    private val liveDataWrapper: LiveDataWrapper,
    private val repository: Repository,
) : ViewModel() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init() {
        liveDataWrapper.update(UiState.Init("Hello World!"))
    }

    fun load() {
        liveDataWrapper.update(UiState.Loading)
        viewModelScope.launch {
            val result = repository.load()
            liveDataWrapper.update(UiState.Loaded(text = result.text))
        }
    }

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
        bundleWrapper.restore()?.let { uiState ->
            liveDataWrapper.update(uiState)
        }
    }

    fun liveData(): LiveData<UiState> {
        return liveDataWrapper.liveData()
    }
}
package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.easycode.zerotoheroandroidtdd.data.repository.Repository
import ru.easycode.zerotoheroandroidtdd.ui.BundleWrapper
import ru.easycode.zerotoheroandroidtdd.ui.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.ui.UiState

class MainViewModel(
    private val liveDataWrapper: LiveDataWrapper.Mutable,
    private val repository: Repository
) : ViewModel() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init(firstInit: Boolean) {
        if (firstInit) {
            liveDataWrapper.update(UiState.Init("Hello World!"))
        }
    }

    fun load() {
        liveDataWrapper.update(UiState.Loading)
        viewModelScope.launch {
            val result = repository.load()
            result.show(liveDataWrapper)
        }
    }

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore){
        bundleWrapper.restore()?.let { uiState ->
            liveDataWrapper.update(uiState)
        }
    }

    fun liveData(): LiveData<UiState> {
        return liveDataWrapper.liveData()
    }
}
package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(
    private val liveDataWrapper : LiveDataWrapper,
    private val repository: Repository,
) : ViewModel() {

    private val viewModeScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init() {
        liveDataWrapper.update(UiState.Init(DATA))
    }

    fun liveDate(): LiveData<UiState> {
        return liveDataWrapper.liveData()
    }

    fun load() {
        liveDataWrapper.update(UiState.Loading)
        viewModeScope.launch {
            repository.load()
            liveDataWrapper.update(UiState.Loaded(DATA))
        }
    }

    fun save(bundleWrapper : BundleWrapper.Save){
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper : BundleWrapper.Restore){
        bundleWrapper.restore()?.let { uiState ->
            liveDataWrapper.update(uiState)
        }
    }

    companion object {
        private const val DATA = "Hello World!"
    }
}
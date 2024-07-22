package ru.easycode.zerotoheroandroidtdd.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    interface Update {
        fun update(value: UiState)
    }

    interface Save {
        fun save(bundleWrapper: BundleWrapper.Save)
        fun liveData(): LiveData<UiState>
    }

    interface Mutable : Save, Update

    class Base : Mutable {

        private val liveData = MutableLiveData<UiState>()

        override fun liveData(): LiveData<UiState> {
            return liveData
        }

        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveData.value?.let { uiState ->
                bundleWrapper.save(uiState)
            }
        }
    }
}
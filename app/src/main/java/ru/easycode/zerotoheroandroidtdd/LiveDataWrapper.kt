package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

interface LiveDataWrapper {

    fun liveData(): LiveData<UiState>
    fun update(uiState: UiState)
    fun save(bundleWrapper: BundleWrapper.Save)

    class Base : LiveDataWrapper {

        private val liveDate = MutableLiveData<UiState>()

        override fun liveData(): LiveData<UiState> {
            return liveDate
        }

        override fun update(uiState: UiState) {
            liveDate.value = uiState
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveDate.value?.let { uiState ->
                bundleWrapper.save(uiState)
            }
        }
    }
}

interface UiState : Serializable {

    fun apply(textView: TextView, button: Button, progressBar: ProgressBar)

    data class Init(
        private val text: String,
    ) : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.text = text
            textView.isVisible = false
            progressBar.isVisible = false
            button.isEnabled = true
        }
    }

    object Loading : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.isVisible = false
            progressBar.isVisible = true
            button.isEnabled = false
        }
    }

    data class Loaded(
        private val text: String,
    ) : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.text = text
            textView.isVisible = true
            progressBar.isVisible = false
            button.isEnabled = true
        }
    }
}
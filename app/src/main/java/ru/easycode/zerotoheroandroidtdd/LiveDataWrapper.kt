package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

interface LiveDataWrapper {

    fun update(uiState: UiState)
    fun liveData(): LiveData<UiState>

    class Base : LiveDataWrapper {

        private val liveData = MutableLiveData<UiState>()

        override fun update(uiState: UiState) {
            liveData.value = uiState
        }

        override fun liveData(): LiveData<UiState> {
            return liveData
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
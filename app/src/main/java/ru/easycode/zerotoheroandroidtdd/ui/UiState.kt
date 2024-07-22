package ru.easycode.zerotoheroandroidtdd.ui

import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import java.io.Serializable

interface UiState : Serializable {

    fun apply(textView: TextView, button: Button, progressBar: ProgressBar)

    data class Init(private val text: String) : UiState{
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.text = text
            textView.isVisible = false
            progressBar.isVisible = false
            button.isEnabled = true
        }
    }

    object Loading : UiState{
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.isVisible = false
            progressBar.isVisible = true
            button.isEnabled = false
        }
    }

    data class Loaded(private val text: String) : UiState{
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.text = text
            textView.isVisible = true
            progressBar.isVisible = false
            button.isEnabled = true
        }
    }
}
package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var uiState: UiState = UiState.Init

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val actionButton = findViewById<Button>(R.id.actionButton)
        val textView = findViewById<TextView>(R.id.titleTextView)
        uiState.apply(textView, actionButton, progressBar)

        actionButton.setOnClickListener {
            uiState = UiState.Loading
            uiState.apply(textView, actionButton, progressBar)
            it.postDelayed({
                uiState = UiState.Loaded
                uiState.apply(textView, actionButton, progressBar)
            }, 3000)
        }
    }
}

interface UiState : Serializable {
    fun apply(textView: TextView, button: Button, progressBar: ProgressBar)

    object Init : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.isVisible = false
            button.isEnabled = true
            progressBar.isVisible = false
        }
    }

    object Loading : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.isVisible = false
            button.isEnabled = false
            progressBar.isVisible = true
        }
    }

    object Loaded : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.isVisible = true
            button.isEnabled = true
            progressBar.isVisible = false
        }
    }
}
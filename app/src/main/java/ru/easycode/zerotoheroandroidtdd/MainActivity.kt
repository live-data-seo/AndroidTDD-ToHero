package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var uiState: UiState = UiState.Init
    private lateinit var textView: TextView
    private lateinit var hideButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        hideButton = findViewById(R.id.hideButton)

        uiState.apply(textView)

        hideButton.setOnClickListener {
            uiState = UiState.TextHide
            uiState.apply(textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, UiState::class.java)?.apply(textView)
        } else {
            (savedInstanceState.getSerializable(KEY) as UiState?)?.apply(textView)
        }
    }

    companion object {
        private const val KEY = "hide_key"
    }
}

interface UiState : Serializable {
    fun apply(textView: TextView)

    object Init : UiState {
        override fun apply(textView: TextView) {
            textView.text = "Hello World!"
        }
    }

    object TextHide : UiState {
        override fun apply(textView: TextView) {
            textView.isVisible = false
        }
    }
}
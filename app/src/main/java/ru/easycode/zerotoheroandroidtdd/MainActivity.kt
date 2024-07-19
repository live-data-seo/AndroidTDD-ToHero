package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var removeButton: Button
    private lateinit var parent: LinearLayout
    private var uiState: UiState = UiState.Init

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        removeButton = findViewById(R.id.removeButton)
        parent = findViewById(R.id.rootLayout)

        if (savedInstanceState == null) {
            uiState.apply(textView, removeButton, parent)
        }

        removeButton.setOnClickListener {
            uiState = UiState.RemovedTextView
            uiState.apply(textView, removeButton, parent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, UiState::class.java)?.apply(textView, removeButton, parent)
        } else {
            (savedInstanceState.getSerializable(KEY) as UiState?)?.apply(textView, removeButton, parent)
        }
    }

    companion object {
        private const val KEY = "key"
    }
}

interface UiState : Serializable {
    fun apply(textView: TextView, button: Button, parent: LinearLayout)

    object Init : UiState {
        override fun apply(textView: TextView, button: Button, parent: LinearLayout) {
            textView.text = "Hello World!"
            button.isEnabled = true
        }
    }

    object RemovedTextView : UiState {
        override fun apply(textView: TextView, button: Button, parent: LinearLayout) {
            parent.removeView(textView)
            button.isEnabled = false
        }
    }
}
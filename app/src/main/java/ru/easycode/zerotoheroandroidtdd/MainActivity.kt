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

        if(savedInstanceState == null){
            uiState.apply(textView, parent)
        }

        removeButton.setOnClickListener {
            uiState = UiState.TextRemoved
            uiState.apply(textView, parent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(REMOVED_KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(REMOVED_KEY, UiState::class.java)?.apply(textView, parent)
        } else {
            (savedInstanceState.getSerializable(REMOVED_KEY) as UiState?)?.apply(textView, parent)
        }
    }

    companion object {
        private const val REMOVED_KEY = "removed_key"
    }
}

interface UiState : Serializable {
    fun apply(textView: TextView, linearLayout: LinearLayout)

    object Init : UiState {
        override fun apply(textView: TextView, linearLayout: LinearLayout) {
            textView.text = "Hello World!"
        }
    }

    object TextRemoved : UiState {
        override fun apply(textView: TextView, linearLayout: LinearLayout) {
            linearLayout.removeView(textView)
        }
    }
}
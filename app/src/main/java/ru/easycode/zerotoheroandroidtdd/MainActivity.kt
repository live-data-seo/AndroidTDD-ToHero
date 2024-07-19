package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var countTextView: TextView
    private lateinit var incrementButton: Button
    private var uiState: UiState = UiState.Init
    private var count: Count = Count.Base(step = 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countTextView = findViewById(R.id.countTextView)
        incrementButton = findViewById(R.id.incrementButton)

        if (savedInstanceState == null) {
            uiState.apply(countTextView)
        }

        incrementButton.setOnClickListener {
            val number = count.increment(number = countTextView.text.toString())
            uiState = UiState.Increment(number)
            uiState.apply(countTextView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, UiState::class.java)?.apply(countTextView)
        } else {
            (savedInstanceState.getSerializable(KEY) as UiState?)?.apply(countTextView)
        }
    }

    companion object {
        private const val KEY = "key"
    }
}
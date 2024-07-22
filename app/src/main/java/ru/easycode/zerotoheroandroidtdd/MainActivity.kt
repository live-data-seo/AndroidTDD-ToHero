package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var countTextView: TextView
    private lateinit var incrementButton: Button
    private lateinit var decrementButton: Button
    private var uiState : UiState = UiState.Init
    private val count : Count = Count.Base(step = 2, max = 4, min = 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        uiState.apply(countTextView, incrementButton, decrementButton)

        incrementButton.setOnClickListener {
            uiState = count.increment(number = countTextView.text.toString())
            uiState.apply(countTextView, incrementButton, decrementButton)
        }

        decrementButton.setOnClickListener {
            uiState = count.decrement(number = countTextView.text.toString())
            uiState.apply(countTextView, incrementButton, decrementButton)
        }

    }

    private fun initViews() {
        countTextView = findViewById(R.id.countTextView)
        incrementButton = findViewById(R.id.incrementButton)
        decrementButton = findViewById(R.id.decrementButton)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(UI_STATE_KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(UI_STATE_KEY, UiState::class.java)?.apply(countTextView, incrementButton, decrementButton)
        }else{
            (savedInstanceState.getSerializable(UI_STATE_KEY) as UiState?)?.apply(countTextView, incrementButton, decrementButton)
        }
    }

    companion object {
        private const val UI_STATE_KEY = "uiStateKey"
    }
}
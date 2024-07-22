package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val viewModel = MainViewModel(LiveDataWrapper.Base(), Repository.Base())
    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        button = findViewById(R.id.actionButton)
        progressBar = findViewById(R.id.progressBar)

        button.setOnClickListener {
            viewModel.load()
        }

        viewModel.liveDate().observe(this) { uiState ->
            uiState.apply(textView, button, progressBar)
        }
    }
}
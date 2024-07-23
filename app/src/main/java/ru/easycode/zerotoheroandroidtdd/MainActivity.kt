package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var uiState: UiState = UiState.Init("Hello World!")
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            updateUiState()
        }

        binding.inputEditText.doAfterTextChanged {
            val text = it.toString()
            if (text.isEmpty()) {
                return@doAfterTextChanged
            }
            uiState = UiState.ChangeInputEditText(text)
            updateUiState()
        }

        binding.actionButton.setOnClickListener {
            val text = binding.inputEditText.text.toString()
            uiState = UiState.SaveTextToTextView(text)
            updateUiState()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(UI_STATE_KEY, uiState)
        Log.d("TAG", "onSaveInstanceState: $uiState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(UI_STATE_KEY, UiState::class.java)?.let { uiState: UiState ->
                this.uiState = uiState
            }
        } else {
            (savedInstanceState.getSerializable(UI_STATE_KEY) as? UiState)?.let { uiState: UiState ->
                this.uiState = uiState
            }
        }
        Log.d("TAG", "onRestoreInstanceState: $uiState")
        updateUiState()
    }

    private fun updateUiState() {
        uiState.apply(
            binding.titleTextView,
            binding.inputEditText,
            binding.actionButton
        )
    }

    companion object {
        private const val UI_STATE_KEY = "uiStateKey"
    }
}

interface UiState : Serializable {

    fun apply(textView: TextView, inputEditText: TextInputEditText, button: Button)

    data class Init(private val text: String) : UiState {
        override fun apply(textView: TextView, inputEditText: TextInputEditText, button: Button) {
            inputEditText.setText("")
            textView.text = "Hello World!"
            button.isEnabled = false
        }
    }

    data class ChangeInputEditText(private val text: String) : UiState {
        override fun apply(textView: TextView, inputEditText: TextInputEditText, button: Button) {
            button.isEnabled = text.length == 3
        }
    }

    data class SaveTextToTextView(private val text: String) : UiState {
        override fun apply(textView: TextView, inputEditText: TextInputEditText, button: Button) {
            Log.d("TAG", "SaveTextToTextView apply: $text")
            inputEditText.setText("")
            textView.text = text
            button.isEnabled = false
        }
    }
}
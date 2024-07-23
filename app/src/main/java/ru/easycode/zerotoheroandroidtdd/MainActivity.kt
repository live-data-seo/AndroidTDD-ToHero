package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.textfield.TextInputEditText
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var uiState: UiState = UiState.Init
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            update()
        }

        binding.actionButton.setOnClickListener {
            val text = binding.inputEditText.text.toString()
            if (text.trim().isEmpty()) {
                return@setOnClickListener
            }
            uiState = UiState.CreateTextToList(text)
            update()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val textList = ArrayList<String>()
        binding.contentLayout.children.forEach { view ->
            (view as? TextView)?.text?.let { text ->
                textList.add(text.toString())
            }
        }
        uiState = UiState.Save(textList)
        outState.putSerializable(KEY, uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        (savedInstanceState.getSerializable(KEY) as? UiState)?.let {
            uiState = it
            update()
        }
    }

    private fun update() {
        uiState.apply(
            binding.inputEditText,
            binding.actionButton,
            binding.contentLayout
        )
    }

    companion object {
        private const val KEY = "uiStateKey"
    }
}

interface UiState : Serializable {

    fun apply(textInputEditText: TextInputEditText, button: Button, linearLayout: LinearLayout)

    interface Abstract {
        fun restore(linearLayout: LinearLayout, text: String)
    }

    abstract class Base : Abstract, UiState {
        override fun restore(linearLayout: LinearLayout, text: String) {
            val textView = TextView(linearLayout.context)
            textView.text = text
            linearLayout.addView(textView)
        }
    }

    object Init : Base() {
        override fun apply(textInputEditText: TextInputEditText, button: Button, linearLayout: LinearLayout) {
            textInputEditText.setText("")
            linearLayout.removeAllViews()
        }
    }

    data class CreateTextToList(
        private val text: String,
    ) : Base() {
        override fun apply(textInputEditText: TextInputEditText, button: Button, linearLayout: LinearLayout) {
            textInputEditText.setText("")
            restore(linearLayout, text)
        }
    }

    data class Save(
        val textList: ArrayList<String>,
    ) : Base() {
        override fun apply(textInputEditText: TextInputEditText, button: Button, linearLayout: LinearLayout) {
            textList.forEach { text ->
                restore(linearLayout, text)
            }
        }
    }
}
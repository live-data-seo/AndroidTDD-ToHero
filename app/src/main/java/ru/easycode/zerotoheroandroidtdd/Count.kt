package ru.easycode.zerotoheroandroidtdd

import android.widget.TextView
import java.io.Serializable

interface Count {

    fun increment(number: String): String

    class Base(private val step: Int) : Count {

        init {
            if (step == 0 || step < 0) {
                throw IllegalStateException("step should be positive, but was -2")
            }
        }

        override fun increment(number: String): String {
            val numberInt = number.toInt()
            val testNumber = numberInt + step
            return testNumber.toString()
        }
    }
}

interface UiState : Serializable {
    fun apply(textView: TextView)

    object Init : UiState {
        override fun apply(textView: TextView) {
            textView.text = "0"
        }
    }

    data class Increment(private val count: String) : UiState {
        override fun apply(textView: TextView) {
            textView.text = count
        }
    }
}
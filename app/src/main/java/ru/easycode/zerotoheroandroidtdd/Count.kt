package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import java.io.Serializable

interface Count {

    fun initial(number: String): UiState
    fun increment(number: String): UiState
    fun decrement(number: String): UiState

    data class Base(
        private val step: Int,
        private val max: Int,
        private val min: Int,
    ) : Count {

        init {
            if (step < 1) {
                throw IllegalStateException("step should be positive, but was -2")
            }
            if (max < 1) {
                throw IllegalStateException("max should be positive, but was -2")
            }
            if (max < step) {
                throw IllegalStateException("max should be more than step")
            }
            if (max < min) {
                throw IllegalStateException("max should be more than min")
            }
        }

        override fun initial(number: String): UiState {
            return when (number.toInt()) {
                min -> {
                    UiState.Min(text = number)
                }

                max -> {
                    UiState.Max(text = number)
                }

                else -> {
                    UiState.Base(text = number)
                }
            }
        }

        override fun increment(number: String): UiState {
            val numberInt = number.toInt()
            val incremented = numberInt + step
            if (incremented == max) {
                return UiState.Max("${step + numberInt}")
            }
            return UiState.Base("${step + numberInt}")
        }

        override fun decrement(number: String): UiState {
            val numberInt = number.toInt()
            val decremented = numberInt - step
            if (decremented == min) {
                return UiState.Min(text = "$decremented")
            }
            return UiState.Base(text = "$decremented")
        }
    }

}

interface UiState : Serializable {

    fun apply(textView: TextView, incrementButton: Button, decrementButton: Button)


    object Init : UiState {
        override fun apply(
            textView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            textView.text = "0"
            incrementButton.isEnabled = true
            decrementButton.isEnabled = false
        }
    }

    data class Min(private val text: String) : UiState {
        override fun apply(
            textView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            textView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = false
        }
    }

    data class Base(private val text: String) : UiState {
        override fun apply(
            textView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            textView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = true
        }
    }

    data class Max(private val text: String) : UiState {
        override fun apply(
            textView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            textView.text = text
            incrementButton.isEnabled = false
            decrementButton.isEnabled = true
        }
    }
}
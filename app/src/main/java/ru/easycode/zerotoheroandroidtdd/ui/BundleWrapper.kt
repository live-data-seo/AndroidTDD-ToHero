package ru.easycode.zerotoheroandroidtdd.ui

import android.os.Build
import android.os.Bundle

interface BundleWrapper {

    interface Save {
        fun save(uiState: UiState)
    }

    interface Restore {
        fun restore(): UiState?
    }

    interface Mutable : Save, Restore

    class Base(
        private val bundle: Bundle
    ) : Mutable {

        override fun save(uiState: UiState) {
            bundle.putSerializable(UI_STATE_KEY, uiState)
        }

        override fun restore(): UiState? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(UI_STATE_KEY, UiState::class.java)
            } else {
                bundle.getSerializable(UI_STATE_KEY) as? UiState
            }
        }

        companion object {
            private const val UI_STATE_KEY = "uiStateKey"
        }
    }
}
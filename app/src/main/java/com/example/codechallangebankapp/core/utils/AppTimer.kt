package com.example.codechallangebankapp.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object AppTimer {
    private const val LOGOUT_DELAY = 2 * 60 * 1000L // 2 minutos

    private var timerJob: kotlinx.coroutines.Job? = null

    fun startTimer(onTimerExpired: () -> Unit) {
        timerJob?.cancel() // Cancela el temporizador existente si hay alguno

        timerJob = CoroutineScope(Dispatchers.Main).launch {
            delay(LOGOUT_DELAY)
            onTimerExpired()
        }
    }

    fun cancelTimer() {
        timerJob?.cancel()
    }
}
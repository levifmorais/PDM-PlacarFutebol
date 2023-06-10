package com.example.placarfutebol

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.CountDownTimer
import android.os.Vibrator
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat

interface CountUpListener {
    fun onGameEnd()

    fun onIncrementStart()

    fun changeTime()
}

class CountUpTimer (private val textView: TextView, private val acrescimoView: TextView, private val context: Context,
                    private val listener: CountUpListener, private val duration: Long, private val timeMinutes : Int, private val prorroga : Boolean) {

    private var isPaused : Boolean = true
    private var counter : Int = 0
    private var vibrator : Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private lateinit var incrementTimer: CountDownTimer
    private lateinit var overtimeTimer: CountDownTimer
    private lateinit var countUpTimer: CountDownTimer

    fun start() {
        counter = 0

        // MUDAR NO PROPRIO TIMER PARA INICIAR A PRORROGACAO
        var prorrogaDuration = 0

        if (prorroga) {
            prorrogaDuration = ((timeMinutes * 0.3).toInt())
        }

        countUpTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!isPaused) {

                    counter++
                    val minutes = counter / 60
                    val seconds = counter % 60
                    val timeElapsedFormatted = String.format("%02d:%02d", minutes, seconds)
                    textView.text = timeElapsedFormatted

                    Log.d("CountUpTimer", "$prorroga")

                    /*if(counter >= timeMinutes && prorroga){
                        resetIncrementTimer()
                        pause()
                        startOvertimeTimer()
                    }
                    if (counter >= timeMinutes && !prorroga) {
                        listener.onGameEnd()
                    } */


                    // TODO: Precisa ativar o toggleButton
                    if(counter == timeMinutes/2){
                        //intervaloTextView.text = "2°"
                        //pauseCountUpTimer()
                        if (vibrator.hasVibrator() && ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                            val pattern = longArrayOf(0, 250, 250, 250)
                            val repeatIndex = -1
                            vibrator.vibrate(pattern, repeatIndex)
                        }
                        startIncrementTimer()
                        pause()
                    }

                    if(counter == timeMinutes){
                        if (vibrator.hasVibrator() && ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                            val pattern = longArrayOf(0, 250, 250, 250)
                            val repeatIndex = -1
                            vibrator.vibrate(pattern, repeatIndex)
                        }
                        startIncrementTimer()
                        pause()
                    }
                    else if (counter == timeMinutes + prorrogaDuration) {
                        pause()
                        listener.onGameEnd()
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                //Apenas usada em CountDownTimer e nao em CountUpTimer
            }
        }

        //countUpTimer.start()
    }

    fun pause() {
        isPaused = true
        countUpTimer.cancel()
    }

    fun resume() {
        isPaused = false
        countUpTimer.start()
    }

    fun stop() {
        countUpTimer.cancel()
    }

    fun destroy() {
        if (::countUpTimer.isInitialized){
            countUpTimer.cancel()
        }
    }

    fun isPaused() : Boolean {
        return isPaused
    }

    fun setPaused(paused : Boolean) {
        isPaused = paused
    }


    private fun startIncrementTimer() {
        var incrementCounter = 0
        incrementTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                incrementCounter++
                val minutes = incrementCounter / 60
                val seconds = incrementCounter % 60
                val timeElapsedFormatted = String.format("%02d:%02d", minutes, seconds)
                acrescimoView.text = "($timeElapsedFormatted)"
            }

            override fun onFinish() {
                // Nao faz nada
            }
        }
        listener.onIncrementStart()
        incrementTimer.start()
    }

    fun resetIncrementTimer() {
        if (::incrementTimer.isInitialized) {
            incrementTimer.cancel()
            acrescimoView.text = ""
        }
    }
}
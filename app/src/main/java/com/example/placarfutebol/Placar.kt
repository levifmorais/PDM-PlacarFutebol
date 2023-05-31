package com.example.placarfutebol

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class Placar : AppCompatActivity() {

    private lateinit var countUpTimer: CountDownTimer
    private lateinit var timeTextView : TextView
    private lateinit var btnStart : ToggleButton
    private lateinit var golTimeUm : CustomNumberPicker
    private lateinit var golTimeDois : CustomNumberPicker
    private var isPaused : Boolean = false
    private var counter : Int = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)

        golTimeUm = CustomNumberPicker(findViewById(R.id.golUm), 0, 99)
        golTimeDois = CustomNumberPicker(findViewById(R.id.golDois), 0, 99)


        timeTextView = findViewById(R.id.duracaoDePartida)

        timeTextView.text = "00:00"

        val timeMatch = intent.getIntExtra("timeMatch", 0)

        btnStart = findViewById(R.id.btnStartTimer)

        val duration : Long = timeMatch.toLong() * 60 * 1000
        val timeMinutes = timeMatch * 60

        startCountUpTimer(duration, timeMinutes)

        btnStart.setOnClickListener {
            if (isPaused) {
                resumeCountUpTimer()
                btnStart.text = "Pause"
            } else {
                pauseCountUpTimer()
                btnStart.text = "Resume"
            }
        }
    }

    // TODO: Transformar em Classe
    private fun startCountUpTimer(duration: Long, timeMinutes : Int) {
        counter = 0

        countUpTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!isPaused) {

                    counter++
                    val minutes = counter / 60
                    val seconds = counter % 60
                    val timeElapsedFormatted = String.format("%02d:%02d", minutes, seconds)
                    timeTextView.text = timeElapsedFormatted

                    if (counter >= timeMinutes) {
                        stopCountUpTimer()
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                //Apenas usada em CountDownTimer e nao em CountUpTimer
                //timeTextView.text = "00:00"
            }
        }

        countUpTimer.start()
    }

    private fun pauseCountUpTimer() {
        isPaused = true
        countUpTimer.cancel()
    }

    private fun resumeCountUpTimer() {
        isPaused = false
        countUpTimer.start()
    }

    private fun stopCountUpTimer() {
        countUpTimer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countUpTimer.isInitialized) {
            countUpTimer.cancel()
        }
    }

}
package com.example.placarfutebol

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class Placar : AppCompatActivity() {

    private lateinit var countUpTimer: CountDownTimer
    private lateinit var timeTextView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)

        timeTextView = findViewById<TextView>(R.id.duracaoDePartida)

        timeTextView.text = "00:00"

        val timeMatch = intent.getIntExtra("timeMatch", 0)


        val btnStart = findViewById<TextView>(R.id.btnStartTimer)

        btnStart.setOnClickListener {
            startCountUpTimer(timeMatch.toLong() * 60 * 1000)
        }
    }

    private fun startCountUpTimer(duration: Long) {
        var counter = 0

        countUpTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter++
                val minutes = counter / 60
                val seconds = counter % 60
                val timeElapsedFormatted = String.format("%02d:%02d", minutes, seconds)
                timeTextView.text = timeElapsedFormatted

                if (counter >= 90) {
                    stopCountUpTimer()
                }
            }

            private fun stopCountUpTimer() {
                countUpTimer.cancel()
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                //Apenas usada em CountDownTimer e nao em CountUpTimer
                //timeTextView.text = "00:00"
            }
        }

        countUpTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countUpTimer.isInitialized) {
            countUpTimer.cancel()
        }
    }

}
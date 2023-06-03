package com.example.placarfutebol

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class Placar : AppCompatActivity() {
    private lateinit var countUpTimer: CountDownTimer
    private lateinit var timeTextView : TextView
    private lateinit var intervaloTextView : TextView
    private lateinit var acrescimosTextView: TextView
    private lateinit var nomeTimeUm : TextView
    private lateinit var nomeTimeDois : TextView
    private lateinit var btnStart : ToggleButton
    private lateinit var golTimeUm : CustomNumberPicker
    private lateinit var golTimeDois : CustomNumberPicker
    private lateinit var timer : CountUpTimer

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Inicializa o numberPicker dos gols
        golTimeUm = CustomNumberPicker(findViewById(R.id.golUm), 0, 99)
        golTimeDois = CustomNumberPicker(findViewById(R.id.golDois), 0, 99)

        // Inicializa os nomes dos times de acordo com a config
        nomeTimeUm = findViewById(R.id.nomeTimeUm)
        nomeTimeDois = findViewById(R.id.nomeTimeDois)

        val editNomeTimeUm = intent.getStringExtra("editNomeTimeUm")
        val editNomeTimeDois = intent.getStringExtra("editNomeTimeDois")

        nomeTimeUm.text = editNomeTimeUm
        nomeTimeDois.text = editNomeTimeDois

        // Inicializa o intervalo
        intervaloTextView = findViewById(R.id.tempoDePartida)

        intervaloTextView.text = "1°"

        // Inicializa o tempo de partida
        timeTextView = findViewById(R.id.duracaoDePartida)

        timeTextView.text = "00:00"

        // Inicializa o tempo de acréscimos
        acrescimosTextView = findViewById(R.id.acrescimosDePartida)

        acrescimosTextView.text = ""


        val timeMatch = intent.getIntExtra("timeMatch", 0) * 60

        btnStart = findViewById(R.id.btnStartTimer)

        val duration : Long = timeMatch.toLong() * 60 * 1000

        timer = CountUpTimer(timeTextView, acrescimosTextView, this, duration, timeMatch)
        timer.start()

        // Pausa a partida
        btnStart.setOnClickListener {
            if (timer.isPaused()) {
                timer.resume()
                btnStart.text = "Pause"
            } else {
                timer.pause()
                btnStart.text = "Resume"
            }
        }

    }

}
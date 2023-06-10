package com.example.placarfutebol

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity


class Placar : AppCompatActivity(), CountUpListener {
    private lateinit var timeTextView : TextView
    private lateinit var intervaloTextView : TextView
    private lateinit var acrescimosTextView: TextView
    private lateinit var nomeTimeUm : TextView
    private lateinit var nomeTimeDois : TextView
    private lateinit var btnStart : ToggleButton
    private lateinit var golTimeUm : CustomNumberPicker
    private lateinit var golTimeDois : CustomNumberPicker
    private lateinit var timer : CountUpTimer
    private lateinit var btnGameEnd : Button
    private lateinit var btnIntervalo : Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)


        btnIntervalo = findViewById(R.id.btnIntervalo)
        btnIntervalo.visibility = Button.GONE

        btnGameEnd = findViewById(R.id.btnGameEnd)
        btnGameEnd.visibility = Button.GONE

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

        val switchProrrogaChecked = intent.getBooleanExtra("switchProrrogaChecked", false)

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

        timer = CountUpTimer(timeTextView, acrescimosTextView, this, this, duration, timeMatch, switchProrrogaChecked)
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

        btnGameEnd.setOnClickListener {
            val resultado = StackResultado(nomeTimeUm.text.toString(), nomeTimeDois.text.toString(), golTimeUm.getValue(), golTimeDois.getValue())
            StackResultados.push(resultado)
            Log.d("StackResultados", StackResultados.peek().toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        var intervalo = 0
        btnIntervalo.setOnClickListener {
            timer.resetIncrementTimer()
            if (intervalo == 0) {
                intervaloTextView.text = "2°"
                btnStart.visibility = ToggleButton.VISIBLE
                btnIntervalo.visibility = Button.GONE
                intervalo += 1
            }
            else if(intervalo == 1 && !switchProrrogaChecked || intervalo == 2) {
                val resultado = StackResultado(nomeTimeUm.text.toString(), nomeTimeDois.text.toString(), golTimeUm.getValue(), golTimeDois.getValue())
                StackResultados.push(resultado)
                Log.d("StackResultados", StackResultados.peek().toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else if (intervalo == 1 && switchProrrogaChecked){
                intervaloTextView.text = "PRORROGAÇÃO"
                btnStart.visibility = ToggleButton.VISIBLE
                btnIntervalo.visibility = Button.GONE
                intervalo += 1
            }
        }

    }

    override fun onGameEnd() {
        btnGameEnd.visibility = Button.VISIBLE
        btnStart.visibility = ToggleButton.GONE
    }

    override fun onIncrementStart() {
        btnStart.toggle()
        btnIntervalo.visibility = Button.VISIBLE
        btnStart.visibility = ToggleButton.GONE
    }

    override fun changeTime() {
        TODO("Not yet implemented")
    }

}
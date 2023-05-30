package com.example.placarfutebol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = CriacaoPartidaDialogFragment()
        val btnCriarPartida : Button = findViewById(R.id.btnCriarPartida)

        // Mostra o dialog para criar a partida
        btnCriarPartida.setOnClickListener {
            dialog.show(supportFragmentManager, "CriacaoPartidaDialogFragment")
        }

    }
}
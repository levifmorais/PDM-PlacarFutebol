package com.example.placarfutebol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity(), CriacaoPartidaDialogListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = CriacaoPartidaDialogFragment()
        dialog.dialogListener = this
        val btnCriarPartida : Button = findViewById(R.id.btnCriarPartida)

        // Mostra o dialog para criar a partida
        btnCriarPartida.setOnClickListener {
            dialog.show(supportFragmentManager, "CriacaoPartidaDialogFragment")
        }

    }

    override fun onDialogPositiveClick(timeMatch: Int) {
        val intent = Intent(this, Placar::class.java)
        intent.putExtra("timeMatch", timeMatch)
        startActivity(intent)
    }
}
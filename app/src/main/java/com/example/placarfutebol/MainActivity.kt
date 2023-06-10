package com.example.placarfutebol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity(), CriacaoPartidaDialogListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StackResultados.load(this)

        val dialogCriacaoPartida = CriacaoPartidaDialogFragment()
        dialogCriacaoPartida.dialogListener = this

        val dialogHistorico = HistoricoDialog(this)

        val btnCriarPartida : Button = findViewById(R.id.btnCriarPartida)

        val btnHistorico : Button = findViewById(R.id.historico)

        // Mostra o dialogCriacaoPartida para criar a partida
        btnCriarPartida.setOnClickListener {
            dialogCriacaoPartida.show(supportFragmentManager, "CriacaoPartidaDialogFragment")
        }

        // Mostra o historico de partidas
        btnHistorico.setOnClickListener {
            dialogHistorico.show(supportFragmentManager, "HistoricoDialog")
        }

    }

    // Variaveis que serao passadas para a activity Placar
    override fun onDialogPositiveClick(
        timeMatch: Int,
        editNomeTimeUm: String,
        editNomeTimeDois: String,
        switchProrrogaChecked: Boolean
    ) {
        val intent = Intent(this, Placar::class.java)
        intent.putExtra("timeMatch", timeMatch)
        intent.putExtra("editNomeTimeUm", editNomeTimeUm)
        intent.putExtra("editNomeTimeDois", editNomeTimeDois)
        intent.putExtra("switchProrrogaChecked", switchProrrogaChecked)
        startActivity(intent)
    }
}
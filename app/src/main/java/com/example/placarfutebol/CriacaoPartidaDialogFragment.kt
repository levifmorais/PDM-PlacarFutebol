package com.example.placarfutebol

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

/*
    Esta classe cria um dialog para configurar a partida

    Modo de uso:
    val dialog = CriacaoPartidaDialogFragment()
    dialog.show(supportFragmentManager, *string)
 */

// Listener que envia a configuracao da partida para a activity de Placar
interface CriacaoPartidaDialogListener {
    fun onDialogPositiveClick(timeMatch : Int)
}
 class CriacaoPartidaDialogFragment : DialogFragment() {


     // TODO: Salvar a ultima configuracao de partida

     var dialogListener: CriacaoPartidaDialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return activity?.let {

            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_config_partida, null)

            val playersOne = CustomNumberPicker(view.findViewById(R.id.quantidadeJogadoresUm), 1, 11)
            val playersTwo = CustomNumberPicker(view.findViewById(R.id.quantidadeJogadoresDois), 1, 11)

            builder.setView(view)
                .setPositiveButton("Pronto"
                ) { _, _ ->
                    val timeMatch = view.findViewById<EditText>(R.id.tempoTextNumber)

                    dialogListener?.onDialogPositiveClick(timeMatch.text.toString().toInt())

                }
                .setNegativeButton("Cancelar"
                ) { _, _ ->
                    // User cancelled the dialog
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

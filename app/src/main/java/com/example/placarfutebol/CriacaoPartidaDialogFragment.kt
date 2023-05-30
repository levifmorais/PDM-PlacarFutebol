package com.example.placarfutebol

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/*
    Esta classe cria um dialog para configurar a partida

    Modo de uso:
    val dialog = CriacaoPartidaDialogFragment()
    dialog.show(supportFragmentManager, *string)
 */

 class CriacaoPartidaDialogFragment : DialogFragment() {


     // TODO: Salvar a ultima configuracao de partida
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_config_partida, null)

            CustomNumberPicker(view.findViewById(R.id.quantidadeJogadoresUm), 1, 11)
            CustomNumberPicker(view.findViewById(R.id.quantidadeJogadoresDois), 1, 11)

            builder.setView(view)
                .setPositiveButton("Pronto",
                    DialogInterface.OnClickListener { dialog, id ->
                        // START THE GAME!
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
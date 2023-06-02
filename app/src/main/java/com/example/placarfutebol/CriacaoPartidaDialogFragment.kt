package com.example.placarfutebol

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar

/*
    Esta classe cria um dialog para configurar a partida

    Modo de uso:
    val dialog = CriacaoPartidaDialogFragment()
    dialog.show(supportFragmentManager, *string)
 */

// Listener que envia a configuracao da partida para a activity de Placar
interface CriacaoPartidaDialogListener {
    fun onDialogPositiveClick(timeMatch : Int, editNomeTimeUm : String, editNomeTimeDois : String)
}
 class CriacaoPartidaDialogFragment : DialogFragment() {


     // TODO: Salvar a ultima configuracao de partida

     var dialogListener: CriacaoPartidaDialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return activity?.let {

            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_config_partida, null)

            val imageTeamOne = view.findViewById<ImageView>(R.id.imagemTimeUm)
            val imageTeamTwo = view.findViewById<ImageView>(R.id.imagemTimeDois)

            imageTeamOne.setImageDrawable(null);imageTeamTwo.setImageDrawable(null)

            val playersOne = CustomNumberPicker(view.findViewById(R.id.quantidadeJogadoresUm), 1, 11)
            val playersTwo = CustomNumberPicker(view.findViewById(R.id.quantidadeJogadoresDois), 1, 11)

            builder.setView(view)
                .setPositiveButton("Pronto"
                ) { _, _ ->

                    // Pega as variaveis que serao Intent e as transforma
                    val timeMatch = view.findViewById<EditText>(R.id.tempoTextNumber)

                    if (timeMatch.text.isEmpty()) {
                        val snackbar = Snackbar.make(view, "Preencha a duração da partida", Snackbar.LENGTH_SHORT)
                        snackbar.show()
                    }
                    else {
                        val editNomeTimeUm = view.findViewById<EditText>(R.id.editNomeTimeUm)
                        val editNomeTimeDois = view.findViewById<EditText>(R.id.editNomeTimeDois)

                        dialogListener?.onDialogPositiveClick(
                            timeMatch.text.toString().toInt(),
                            editNomeTimeUm.text.toString(),
                            editNomeTimeDois.text.toString()
                        )
                    }
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

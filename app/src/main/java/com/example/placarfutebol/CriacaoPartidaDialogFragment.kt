package com.example.placarfutebol

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
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
    fun onDialogPositiveClick(timeMatch : Int, editNomeTimeUm : String, editNomeTimeDois : String, switchProrrogaChecked : Boolean)
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

            val switchLocal = view.findViewById<LinearLayout>(R.id.Local)
            switchLocal.visibility = View.GONE

            val switchProrroga = view.findViewById<SwitchCompat>(R.id.switchProrrogacao)

            imageTeamOne.setImageDrawable(null);imageTeamTwo.setImageDrawable(null)

            builder.setView(view)
                .setPositiveButton("Pronto"
                ) { _, _ ->

                    // Pega as variaveis que serao Intent e as transforma
                    val timeMatch = view.findViewById<EditText>(R.id.tempoTextNumber)

                    if (timeMatch.text.isEmpty()) {
                        val activityView = (context as Activity).window.decorView.findViewById<View>(android.R.id.content)
                        val snackbar = Snackbar.make(activityView, "Preencha a duração da partida", Snackbar.LENGTH_INDEFINITE)
                        snackbar.show()
                    }
                    else {
                        val editNomeTimeUm = view.findViewById<EditText>(R.id.editNomeTimeUm)
                        val editNomeTimeDois = view.findViewById<EditText>(R.id.editNomeTimeDois)

                        dialogListener?.onDialogPositiveClick(
                            timeMatch.text.toString().toInt(),
                            editNomeTimeUm.text.toString(),
                            editNomeTimeDois.text.toString(),
                            switchProrroga.isChecked
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

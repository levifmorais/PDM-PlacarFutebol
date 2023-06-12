package com.example.placarfutebol

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
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
    fun onDialogPositiveClick(timeMatch : Int, editNomeTimeUm : String, editNomeTimeDois : String, switchProrrogaChecked : Boolean, switchPenaltiesChecked: Boolean)
}
class CriacaoPartidaDialogFragment : DialogFragment() {

    private lateinit var sharedPreferences: SharedPreferences
    var dialogListener: CriacaoPartidaDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        sharedPreferences = requireContext().getSharedPreferences("dialog_states", Context.MODE_PRIVATE)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_config_partida, null)

            val imageTeamOne = view.findViewById<ImageView>(R.id.imagemTimeUm)
            val imageTeamTwo = view.findViewById<ImageView>(R.id.imagemTimeDois)
            val switchLocal = view.findViewById<LinearLayout>(R.id.Local)
            switchLocal.visibility = View.GONE
            val switchProrroga = view.findViewById<SwitchCompat>(R.id.switchProrrogacao)
            val switchPenalties = view.findViewById<SwitchCompat>(R.id.switchPenalties) // Adicione o novo switch

            imageTeamOne.setImageDrawable(null)
            imageTeamTwo.setImageDrawable(null)

            val timeMatchEditText = view.findViewById<EditText>(R.id.tempoTextNumber)
            val editNomeTimeUmEditText = view.findViewById<EditText>(R.id.editNomeTimeUm)
            val editNomeTimeDoisEditText = view.findViewById<EditText>(R.id.editNomeTimeDois)

            val savedTimeMatch = sharedPreferences.getInt("timeMatch", 0)
            val savedEditNomeTimeUm = sharedPreferences.getString("editNomeTimeUm", "")
            val savedEditNomeTimeDois = sharedPreferences.getString("editNomeTimeDois", "")
            val savedSwitchProrrogaChecked = sharedPreferences.getBoolean("switchProrrogaChecked", false)
            val savedSwitchPenaltiesChecked = sharedPreferences.getBoolean("switchPenaltiesChecked", false) // Obtenha o valor do novo switch

            timeMatchEditText.setText(savedTimeMatch.toString())
            editNomeTimeUmEditText.setText(savedEditNomeTimeUm)
            editNomeTimeDoisEditText.setText(savedEditNomeTimeDois)
            switchProrroga.isChecked = savedSwitchProrrogaChecked
            switchPenalties.isChecked = savedSwitchPenaltiesChecked // Defina o valor do novo switch

            builder.setView(view)
                .setPositiveButton("Pronto") { _, _ ->
                    val timeMatch = timeMatchEditText.text.toString().toIntOrNull()
                    val editNomeTimeUm = editNomeTimeUmEditText.text.toString()
                    val editNomeTimeDois = editNomeTimeDoisEditText.text.toString()

                    if (timeMatch == null || editNomeTimeUm.isEmpty() || editNomeTimeDois.isEmpty()) {
                        val activityView = (context as Activity).window.decorView.findViewById<View>(android.R.id.content)
                        val snackbar = Snackbar.make(activityView, "Preencha todos os campos", Snackbar.LENGTH_INDEFINITE)
                        snackbar.show()
                    } else {
                        val editor = sharedPreferences.edit()
                        editor.putInt("timeMatch", timeMatch)
                        editor.putString("editNomeTimeUm", editNomeTimeUm)
                        editor.putString("editNomeTimeDois", editNomeTimeDois)
                        editor.putBoolean("switchProrrogaChecked", switchProrroga.isChecked)
                        editor.putBoolean("switchPenaltiesChecked", switchPenalties.isChecked) // Salve o valor do novo switch
                        editor.apply()

                        dialogListener?.onDialogPositiveClick(
                            timeMatch,
                            editNomeTimeUm,
                            editNomeTimeDois,
                            switchProrroga.isChecked,
                            switchPenalties.isChecked // Passe o valor do novo switch
                        )
                    }
                }
                .setNegativeButton("Cancelar") { _, _ ->
                    // User cancelled the dialog
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

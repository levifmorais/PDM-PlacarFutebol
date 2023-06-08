package com.example.placarfutebol

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.util.*

class HistoricoDialog (context: Context) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return activity?.let {

            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_historico, null)

            val lista : RecyclerView = view.findViewById(R.id.historicoLista)
            lista.layoutManager = LinearLayoutManager(context)

            val stackResultados = StackResultados.getStack()
            val adapter = ResultAdapter(stackResultados)
            lista.adapter = adapter



            builder.setView(view)
                .setPositiveButton("Pronto"
                ) { _, _ ->
                    dialog?.dismiss()
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

class ResultAdapter(private val stack: Stack<StackResultado>) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_layout, parent, false)
        return ResultViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val resultado = stack[position]
        holder.bind(resultado)
    }

    override fun getItemCount(): Int {
        return stack.size
    }

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTimeOne: TextView = itemView.findViewById(R.id.nomeTimeUm)
        private val textTimeTwo: TextView = itemView.findViewById(R.id.nomeTimeDois)
        private val golTimeOne: TextView = itemView.findViewById(R.id.golTimeUm)
        private val golTimeTwo: TextView = itemView.findViewById(R.id.golTimeDois)

        fun bind(resultado: StackResultado) {
            textTimeOne.text = resultado.nomeTimeUm
            textTimeTwo.text = resultado.nomeTimeDois
            golTimeOne.text = resultado.golTimeUm.toString()
            golTimeTwo.text = resultado.golTimeDois.toString()
        }
    }
}
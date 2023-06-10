package com.example.placarfutebol

import android.content.Context
import android.content.SharedPreferences
import java.util.*

data class StackResultado(val nomeTimeUm: String, val nomeTimeDois: String, val golTimeUm: Int, val golTimeDois: Int)

class StackResultados {

    companion object {
        private val stack = Stack<StackResultado>()

        fun push(resultado: StackResultado) {
            stack.push(resultado)
        }

        fun pop(): StackResultado {
            return stack.pop()
        }

        fun peek(): StackResultado {
            return stack.peek()
        }

        fun isEmpty(): Boolean {
            return stack.isEmpty()
        }

        fun clear() {
            stack.clear()
        }

        fun size(): Int {
            return stack.size
        }

        fun getStack(): Stack<StackResultado> {
            return stack
        }

        fun save(context: Context) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            val stackSize = StackResultados.size()

            editor.putInt("stackSize", stackSize)

            for (i in 0 until stackSize) {
                val resultado = StackResultados.getStack().elementAt(i)
                editor.putString("nomeTimeUm$i", resultado.nomeTimeUm)
                editor.putString("nomeTimeDois$i", resultado.nomeTimeDois)
                editor.putInt("golTimeUm$i", resultado.golTimeUm)
                editor.putInt("golTimeDois$i", resultado.golTimeDois)
            }

            editor.apply()
        }

        fun load(context: Context) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

            val stackSize = sharedPreferences.getInt("stackSize", 0)

            clear()

            for (i in 0 until stackSize) {
                val nomeTimeUm = sharedPreferences.getString("nomeTimeUm$i", "") ?: ""
                val nomeTimeDois = sharedPreferences.getString("nomeTimeDois$i", "") ?: ""
                val golTimeUm = sharedPreferences.getInt("golTimeUm$i", 0)
                val golTimeDois = sharedPreferences.getInt("golTimeDois$i", 0)

                val resultado = StackResultado(nomeTimeUm, nomeTimeDois, golTimeUm, golTimeDois)
                push(resultado)
            }
        }
    }


}







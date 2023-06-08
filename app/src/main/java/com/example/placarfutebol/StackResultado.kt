package com.example.placarfutebol

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
    }


}







package com.example.placarfutebol

import android.widget.NumberPicker

/*
    Esta classe facilita a criacao de um NumberPicker

    Modo de uso:
    val numberPicker = CustomNumberPicker(*id, *minValue, *maxValue)
 */
class CustomNumberPicker(private val newNumberPicker: NumberPicker, private val minValue : Int, private val maxValue : Int){

    init {
        newNumberPicker.minValue = minValue
        newNumberPicker.maxValue = maxValue
        newNumberPicker.wrapSelectorWheel = false
    }

    fun getValue(): Int {
        return newNumberPicker.value
    }
}
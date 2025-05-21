
// MiPila.kt
package com.agenciacristal.calculadora

import android.util.Log

/**
 * Implementación de una pila de números para lógica RPN.
 */
class MiPila {
    private val elementos = mutableListOf<Double>()

    /** Inserta un valor arriba de la pila */
    fun push(valor: Double) {
        elementos.add(valor)
        Log.d("MiPila", "push($valor) → $elementos")
    }

    /** Extrae el valor de la cima */
    fun pop(): Double {
        if (elementos.isEmpty()) throw IllegalStateException("Pila vacía")
        return elementos.removeAt(elementos.lastIndex)
    }

    /** Consulta el valor de la cima sin extraerlo */
    fun peek(): Double {
        if (elementos.isEmpty()) throw IllegalStateException("Pila vacía")
        return elementos.last()
    }

    /** Tamaño actual de la pila */
    fun size(): Int = elementos.size

    /** Limpia todos los elementos */
    fun clear() {
        elementos.clear()
    }

    /** Devuelve una copia de la pila como lista */
    fun toList(): List<Double> = elementos.toList()
}

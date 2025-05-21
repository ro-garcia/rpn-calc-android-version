// MainActivity.kt
package com.agenciacristal.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var tvTemp: TextView    // muestra la entrada en curso
    private lateinit var tvResult: TextView  // muestra la pila y el total

    private var numeroStr: String = ""
    private val pila = MiPila()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTemp   = findViewById(R.id.tvTemp)
        tvResult = findViewById(R.id.tvResult)
    }

    /** Gestiona la pulsación de dígitos y punto */
    fun seleccionarNumero(view: View) {
        val digito = (view as Button).text.toString()
        if (digito == "." && numeroStr.contains(".")) return
        numeroStr += digito
        tvTemp.text = numeroStr
    }

    /** Gestiona operadores y push de número en curso */
    fun cambiarOperador(view: View) {
        // 1) Push del número actual
        if (numeroStr.isNotEmpty()) {
            pila.push(numeroStr.toDouble())
            numeroStr = ""
        }
        // 2) Determinar símbolo lógico
        val symbol = when ((view as Button).text.toString().trim()) {
            "÷" -> "/"
            "X", "x" -> "*"
            else -> view.text.toString().trim()
        }
        // 3) Si hay al menos dos valores, realizar operación
        if (pila.size() >= 2) {
            val b = pila.pop()
            val a = pila.pop()
            val resultado = when (symbol) {
                "+" -> a + b
                "-" -> a - b
                "*" -> a * b
                "/" -> a / b
                "%" -> a % b
                else -> 0.0
            }
            pila.push(resultado)
        }
        actualizarDisplay()
    }

    /** Igual: push de número pendiente y refrescar pantalla */
    fun igual(view: View) {
        if (numeroStr.isNotEmpty()) {
            pila.push(numeroStr.toDouble())
            numeroStr = ""
        }
        actualizarDisplay()
    }

    /** Borra último dígito o limpia toda la pila */
    fun borrar(view: View) {
        when ((view as Button).text.toString().trim()) {
            "C" -> {
                if (numeroStr.isNotEmpty()) {
                    numeroStr = numeroStr.dropLast(1)
                    tvTemp.text = numeroStr
                } else if (pila.size() > 0) {
                    pila.pop()
                    actualizarDisplay()
                }
            }
            "CA" -> {
                numeroStr = ""
                pila.clear()
                actualizarDisplay()
            }
        }
    }

    /** Refresca tvResult mostrando la pila y el total */
    private fun actualizarDisplay() {
        val elements = pila.toList()
        // Mostrar cada elemento en línea separada
        val stackText = if (elements.isEmpty()) "" else elements.joinToString("\n")
        // Sumar todos los elementos
        val total = elements.sum()
        // Mostrar pila y total
        tvResult.text = if (stackText.isEmpty()) {
            "Total: $total"
        } else {
            "$stackText\nTotal: $total"
        }
        // Limpiar entrada actual
        tvTemp.text = ""
    }
}


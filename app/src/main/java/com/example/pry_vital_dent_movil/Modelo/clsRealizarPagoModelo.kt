package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsRealizarPagoModelo(private val contexto: Context) {

    fun ObtenerDetallesPago(alTerminar: (String, String) -> Unit) {
        // Aquí conectarás tu API con Retrofit para traer los datos reales de la deuda
        val concepto = "Endodoncia pieza 36 - sesión 2"
        val total = "$1,000.00"
        alTerminar(concepto, total)
    }

    fun ProcesarPago(medioSeleccionado: String, alTerminar: (Boolean, String) -> Unit) {
        if (medioSeleccionado.isNotEmpty()) {
            // Simulación de envío al backend (PHP/MySQL)
            alTerminar(true, "Pago procesado correctamente")
        } else {
            alTerminar(false, "Seleccione un método de pago válido")
        }
    }
}
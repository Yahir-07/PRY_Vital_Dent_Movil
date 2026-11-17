package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsPagosModelo(private val contexto: Context) {

    fun ObtenerSaldoPendiente(alTerminar: (String, String) -> Unit) {
        // TOD0: Aquí conectarás tu API con Retrofit para traer el saldo pendiente real
        val monto = "$1,800.00"
        val concepto = "Endodoncia pieza 36 - sesión 2"
        alTerminar(monto, concepto)
    }

    fun ObtenerListaPagos(pestana: Int, alTerminar: (List<DataPago>) -> Unit) {
        // TOD0: Aquí conectarás tu API con Retrofit y MySQL para traer el historial de pagos
        val pagosRealizados = listOf(
            DataPago("1", "Ortodoncia · mensualidad", "24 oct 2026 · $950.00", "Aprobado"),
            DataPago("2", "Endodoncia · sesión 1", "2 oct 2026 · $1,000.00", "Aprobado"),
            DataPago("3", "Ortodoncia · mensualidad", "26 sep 2026 · $950.00", "Rechazado")
        )

        val pagosPendientes = listOf(
            DataPago("4", "Endodoncia · sesión 2", "Vence 15 nov 2026 · $1,800.00", "Pendiente")
        )

        // Pestaña 0 = Realizados, Pestaña 1 = Pendientes
        if (pestana == 0) {
            alTerminar(pagosRealizados)
        } else {
            alTerminar(pagosPendientes)
        }
    }
}
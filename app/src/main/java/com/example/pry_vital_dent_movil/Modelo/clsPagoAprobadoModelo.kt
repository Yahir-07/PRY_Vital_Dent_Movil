package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsPagoAprobadoModelo(private val contexto: Context) {

    fun ObtenerDetallesComprobante(alTerminar: (String, String, String, String, String, String) -> Unit) {
        // Aquí conectarás tu API con Retrofit para traer los datos del ticket guardado
        val monto = "$1,800.00"
        val concepto = "Endodoncia - sesión 2"
        val fecha = "31 oct 2026, 9:41"
        val medio = "Visa •••• 4621"
        val autorizado = "Huella digital"
        val folio = "VD-2026-10-00418"

        alTerminar(monto, concepto, fecha, medio, autorizado, folio)
    }
}
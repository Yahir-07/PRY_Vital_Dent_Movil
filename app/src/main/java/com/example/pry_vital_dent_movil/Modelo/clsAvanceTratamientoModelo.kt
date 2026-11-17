package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsAvanceTratamientoModelo(private val contexto: Context) {

    fun ObtenerResumenTratamiento(alTerminar: (String, String, String, String) -> Unit) {
        // Aquí harás la consulta a tu base de datos PHP mediante Retrofit
        val porcentaje = "60 %"
        val sesiones = "9 de 15 sesiones"
        val finEstimado = "Fin estimado: abril 2027"
        val estado = "En curso"

        alTerminar(porcentaje, sesiones, finEstimado, estado)
    }

    fun ObtenerHistorialAvances(alTerminar: (List<String>) -> Unit) {
        val listaAvances = listOf(
            "Sesión 10 - 12 nov: Cambio de arco superior (programada).",
            "Sesión 9 - 24 oct: Ajuste de arcos; alineación inferior al 80 %.",
            "Sesión 8 - 26 sep: Colocación de ligas intermaxilares.",
            "Sesión 7 - 29 ago: Cambio de ligaduras y revisión de higiene."
        )
        alTerminar(listaAvances)
    }
}
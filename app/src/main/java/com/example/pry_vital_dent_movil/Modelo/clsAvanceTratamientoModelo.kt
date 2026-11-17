package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsAvanceTratamientoModelo(private val contexto: Context) {

    fun ObtenerResumenTratamiento(alTerminar: (String, String, String, String) -> Unit) {
        // TOD0: Aquí conectarás tu API PHP mediante Retrofit más adelante
        val porcentaje = "60 %"
        val sesiones = "9 de 15 sesiones"
        val finEstimado = "Fin estimado: abril 2027"
        val estado = "En curso"

        alTerminar(porcentaje, sesiones, finEstimado, estado)
    }

    fun ObtenerHistorialAvances(alTerminar: (List<DataAvance>) -> Unit) {
        // TOD0: Aquí conectarás tu API PHP mediante Retrofit para traer los datos reales
        val listaAvances = listOf(
            DataAvance("1", "Sesión 10", "12 nov", "Cambio de arco superior (programada)."),
            DataAvance("2", "Sesión 9", "24 oct", "Ajuste de arcos; alineación inferior al 80 %."),
            DataAvance("3", "Sesión 8", "26 sep", "Colocación de ligas intermaxilares."),
            DataAvance("4", "Sesión 7", "29 ago", "Cambio de ligaduras y revisión de higiene.")
        )
        alTerminar(listaAvances)
    }
}
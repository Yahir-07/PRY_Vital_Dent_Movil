package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsMisCitasModelo(private val contexto: Context) {

    fun ObtenerListaCitas(alTerminar: (List<DataCita>) -> Unit) {
        val listaCitas = listOf(
            DataCita("1", "Ortodoncia - Ajuste de brackets", "Lunes 24 de Noviembre • 10:00 AM", "Confirmada"),
            DataCita("2", "Limpieza dental profunda", "Viernes 28 de Noviembre • 04:30 PM", "Pendiente")
        )

        alTerminar(listaCitas)
    }
}
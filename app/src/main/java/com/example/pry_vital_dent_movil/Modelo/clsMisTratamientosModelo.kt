package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsMisTratamientosModelo(private val contexto: Context) {

    fun ObtenerTratamientosEnCurso(alTerminar: (List<DataTratamiento>) -> Unit) {
        val listaEnCurso = listOf(
            DataTratamiento(
                idTratamiento = "1",
                nombre = "Ortodoncia Fija",
                detalle = "Inicio: 15 de Enero 2026",
                estado = "En curso",
                sesionActual = 9,
                sesionTotal = 15
            ),
            DataTratamiento(
                idTratamiento = "2",
                nombre = "Endodoncia pieza 36",
                detalle = "Inicio: 1 de Noviembre 2026",
                estado = "En curso",
                sesionActual = 1,
                sesionTotal = 3
            )
        )
        alTerminar(listaEnCurso)
    }

    fun ObtenerTratamientosFinalizados(alTerminar: (List<DataTratamiento>) -> Unit) {
        val listaFinalizados = listOf(
            DataTratamiento(
                idTratamiento = "3",
                nombre = "Limpieza y profilaxis",
                detalle = "Completado en Octubre 2026",
                estado = "Completado",
                sesionActual = 10,
                sesionTotal = 10
            )
        )
        alTerminar(listaFinalizados)
    }
}
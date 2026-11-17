package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsMisTratamientosModelo(private val contexto: Context) {

    fun ObtenerTratamientosEnCurso(alTerminar: (List<String>) -> Unit) {
        // Aquí conectarás tu API con Retrofit
        val listaEnCurso = listOf("Ortodoncia fija - Sesión 9", "Endodoncia pieza 36 - Sesión 1")
        alTerminar(listaEnCurso)
    }

    fun ObtenerTratamientosFinalizados(alTerminar: (List<String>) -> Unit) {
        val listaFinalizados = listOf("Limpieza y profilaxis - Sesión 10")
        alTerminar(listaFinalizados)
    }
}
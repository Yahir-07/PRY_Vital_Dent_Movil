package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsInicioModelo(private val contexto: Context) {

    fun CargarInformacionPanel(alTerminar: (nombre: String, citaTitulo: String, citaDetalle: String, tratamientoNombre: String, pagosTexto: String) -> Unit) {
        // Aquí posteriormente conectarás tu API de PHP / Base de datos
        val nombreUsuario = "Mariana López"
        val citaTitulo = "Ajuste de brackets"
        val citaDetalle = "Jue 12 nov · 10:30 a. m. - Dr. Andrés Ruiz"
        val tratamientoNombre = "Ortodoncia (Sesión 9)"
        val pagosTexto = "1 pendiente"

        alTerminar(nombreUsuario, citaTitulo, citaDetalle, tratamientoNombre, pagosTexto)
    }
}
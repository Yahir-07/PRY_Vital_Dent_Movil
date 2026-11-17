package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsCitaAgendadaModelo(private val contexto: Context) {

    fun ObtenerDetallesCitaReciente(alTerminar: (String, String, String, String, String) -> Unit) {
        // Aquí puedes consultar SharedPreferences o la base de datos local/remota con los datos de la última cita
        val fecha = "Jueves 12 de Noviembre, 2026"
        val hora = "10:30 a. m."
        val motivo = "Ajuste de brackets"
        val doctor = "Dr. Andrés Ruiz"
        val estado = "Pendiente por confirmar"

        alTerminar(fecha, hora, motivo, doctor, estado)
    }
}
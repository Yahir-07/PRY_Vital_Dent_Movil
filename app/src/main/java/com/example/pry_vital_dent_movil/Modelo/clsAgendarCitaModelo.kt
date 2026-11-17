package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsAgendarCitaModelo(private val contexto: Context) {

    fun ObtenerHorariosDisponibles(fecha: String, alTerminar: (List<String>) -> Unit) {
        // Aquí harás la consulta a tu API mediante Retrofit para obtener las horas libres de ese día
        val horasEjemplo = listOf("09:00 a. m.", "10:30 a. m.", "12:00 p. m.", "04:00 p. m.")
        alTerminar(horasEjemplo)
    }

    fun RegistrarCita(fecha: String, hora: String, motivo: String, alTerminar: (Boolean, String) -> Unit) {
        if (fecha.isNotEmpty() && hora.isNotEmpty() && motivo.isNotEmpty()) {
            // Aquí enviarás los datos de la cita a tu base de datos PHP
            alTerminar(true, "¡Cita agendada con éxito!")
        } else {
            alTerminar(false, "Por favor selecciona fecha, hora y motivo")
        }
    }
}
package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsContactoModelo(private val contexto: Context) {

    fun ObtenerDatosContactoHorario(alTerminar: (String, String, String, String, String) -> Unit) {
        // Aquí conectarás tu API con Retrofit si los datos vienen de la BD
        val direccion = "Av. Juárez 45, Centro, Huauchinango, Hgo."
        val telefono = "776 996 1020"
        val horarioSemana = "9:00 a. m. – 6:00 p. m."
        val horarioSabado = "9:00 a. m. – 2:00 p. m."
        val horarioDomingo = "Cerrado"

        alTerminar(direccion, telefono, horarioSemana, horarioSabado, horarioDomingo)
    }
}
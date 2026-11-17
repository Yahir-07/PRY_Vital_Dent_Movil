package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsHistorialMedicoModelo(private val contexto: Context) {

    fun ObtenerAlergias(alTerminar: (List<String>) -> Unit) {
        // TOD0: Aquí conectarás tu API con Retrofit para traer las alergias del paciente
        val alergias = listOf("Penicilina", "Látex")
        alTerminar(alergias)
    }

    fun ObtenerHistorial(filtro: String, alTerminar: (List<DataHistorial>) -> Unit) {
        // TOD0: Aquí conectarás tu API con Retrofit y MySQL para traer el historial médico real
        val listaCompleta = listOf(
            DataHistorial("1", "Cita atendida", "24 oct 2026 - Ajuste de brackets"),
            DataHistorial("2", "Consulta", "24 oct 2026 - Dx: caries profunda pieza 36"),
            DataHistorial("3", "Receta", "24 oct 2026 - Ibuprofeno 400 mg c/8 h x 3 días"),
            DataHistorial("4", "Tratamiento iniciado", "2 oct 2026 - Endodoncia pieza 36")
        )

        // Lógica de filtrado con los objetos DataHistorial
        val listaFiltrada = when (filtro) {
            "Consultas" -> listaCompleta.filter { it.titulo.equals("Consulta", ignoreCase = true) }
            "Recetas" -> listaCompleta.filter { it.titulo.equals("Receta", ignoreCase = true) }
            "Tratamientos" -> listaCompleta.filter { it.titulo.contains("Tratamiento", ignoreCase = true) }
            else -> listaCompleta
        }

        alTerminar(listaFiltrada)
    }
}
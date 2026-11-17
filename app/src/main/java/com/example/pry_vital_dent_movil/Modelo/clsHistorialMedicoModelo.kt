package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsHistorialMedicoModelo(private val contexto: Context) {

    fun ObtenerAlergias(alTerminar: (List<String>) -> Unit) {
        // Aquí conectarás tu API con Retrofit
        val alergias = listOf("Penicilina", "Látex")
        alTerminar(alergias)
    }

    fun ObtenerHistorial(filtro: String, alTerminar: (List<String>) -> Unit) {
        val listaCompleta = listOf(
            "Cita atendida - 24 oct 2026 - Ajuste de brackets",
            "Consulta - 24 oct 2026 - Dic caries profunda pieza 36",
            "Receta - 24 oct 2026 - Ibuprofeno 400 mg c/8 h x 3 días",
            "Tratamiento iniciado - 2 oct 2026 - Endodoncia pieza 36"
        )

        // Lógica sencilla de filtrado de ejemplo
        val listaFiltrada = when (filtro) {
            "Consultas" -> listaCompleta.filter { it.contains("Consulta") }
            "Recetas" -> listaCompleta.filter { it.contains("Receta") }
            "Tratamientos" -> listaCompleta.filter { it.contains("Tratamiento") }
            else -> listaCompleta
        }

        alTerminar(listaFiltrada)
    }
}
package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsHistorialMedicoModelo(private val contexto: Context) {

    // GET historial.php: alergias registradas del paciente
    fun ObtenerAlergias(alTerminar: (List<String>) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerHistorial(clsSesion.ObtenerToken(contexto), "Todo")

        RetrofitClient.Ejecutar(contexto, llamada) { _, _, datos ->
            alTerminar(datos?.alergias ?: emptyList())
        }
    }

    // GET historial.php?filtro=Todo | Consultas | Recetas | Tratamientos (el servidor filtra)
    fun ObtenerHistorial(filtro: String, alTerminar: (Boolean, String, List<DataHistorial>) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerHistorial(clsSesion.ObtenerToken(contexto), filtro)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito, mensaje, datos?.historial ?: emptyList())
        }
    }
}

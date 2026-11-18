package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsMisCitasModelo(private val contexto: Context) {

    // GET citas.php?tipo=proximas | anteriores
    fun ObtenerListaCitas(tipo: String, alTerminar: (Boolean, String, List<DataCita>) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerCitas(clsSesion.ObtenerToken(contexto), tipo)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito, mensaje, datos ?: emptyList())
        }
    }
}

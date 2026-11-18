package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsMisTratamientosModelo(private val contexto: Context) {

    // GET tratamientos.php?estado=en_curso
    fun ObtenerTratamientosEnCurso(alTerminar: (Boolean, String, List<DataTratamiento>) -> Unit) {
        ObtenerTratamientos("en_curso", alTerminar)
    }

    // GET tratamientos.php?estado=finalizado
    fun ObtenerTratamientosFinalizados(alTerminar: (Boolean, String, List<DataTratamiento>) -> Unit) {
        ObtenerTratamientos("finalizado", alTerminar)
    }

    private fun ObtenerTratamientos(estado: String, alTerminar: (Boolean, String, List<DataTratamiento>) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerTratamientos(clsSesion.ObtenerToken(contexto), estado)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito, mensaje, datos ?: emptyList())
        }
    }
}

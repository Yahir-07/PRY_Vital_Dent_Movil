package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsInicioModelo(private val contexto: Context) {

    fun ObtenerNombreGuardado(): String = clsSesion.ObtenerNombre(contexto)

    fun CargarInformacionPanel(
        alTerminar: (nombre: String, citaTitulo: String, citaDetalle: String, tratamientoNombre: String, pagosTexto: String) -> Unit,
        alFallar: (mensaje: String) -> Unit
    ) {
        val llamada = RetrofitClient.api.ObtenerInicio(clsSesion.ObtenerToken(contexto))

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            if (exito && datos != null) {
                alTerminar(datos.nombre, datos.citaTitulo, datos.citaDetalle, datos.tratamientoNombre, datos.pagosTexto)
            } else {
                alFallar(mensaje)
            }
        }
    }
}

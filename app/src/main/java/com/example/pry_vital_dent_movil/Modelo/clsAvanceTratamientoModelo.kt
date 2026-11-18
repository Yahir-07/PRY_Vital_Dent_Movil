package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsAvanceTratamientoModelo(private val contexto: Context) {

    // GET avance_tratamiento.php?id=: resumen del tratamiento y sus sesiones
    // (id vacío = el tratamiento en curso más reciente, como la tarjeta de Inicio)
    fun ObtenerDetalleTratamiento(idTratamiento: String, alTerminar: (Boolean, String, DatosAvanceTratamiento?) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerAvanceTratamiento(clsSesion.ObtenerToken(contexto), idTratamiento)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito && datos != null, mensaje, datos)
        }
    }
}

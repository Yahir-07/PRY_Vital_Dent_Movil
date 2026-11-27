package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsMultimediaModelo(private val contexto: Context) {

    // GET multimedia.php            -> todo
    // GET multimedia.php?tipo=video -> solo videos
    // GET multimedia.php?tipo=audio -> solo audios
    fun ObtenerMultimedia(
        tipo: String?,
        alTerminar: (Boolean, String, List<DataMultimedia>) -> Unit
    ) {
        val llamada = RetrofitClient.api.ObtenerMultimedia(RetrofitClient.URL_CORREOS + "multimedia.php", tipo)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito, mensaje, datos ?: emptyList())
        }
    }
}

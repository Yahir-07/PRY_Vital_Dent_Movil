package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsNuestrosDoctoresModelo(private val contexto: Context) {

    // GET doctores.php: doctores visibles de la tabla doctores
    fun ObtenerListaDoctores(alTerminar: (Boolean, String, List<DataDoctor>) -> Unit) {
        RetrofitClient.Ejecutar(contexto, RetrofitClient.api.ObtenerDoctores()) { exito, mensaje, datos ->
            alTerminar(exito, mensaje, datos ?: emptyList())
        }
    }
}

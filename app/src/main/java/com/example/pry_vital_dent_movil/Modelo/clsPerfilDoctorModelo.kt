package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsPerfilDoctorModelo(private val contexto: Context) {

    // GET doctor.php?id=: datos del doctor y la lista de "Otros doctores"
    fun ObtenerPerfilDoctor(idDoctor: String, alTerminar: (Boolean, String, DatosPerfilDoctor?) -> Unit) {
        RetrofitClient.Ejecutar(contexto, RetrofitClient.api.ObtenerDoctor(idDoctor)) { exito, mensaje, datos ->
            alTerminar(exito && datos != null, mensaje, datos)
        }
    }
}

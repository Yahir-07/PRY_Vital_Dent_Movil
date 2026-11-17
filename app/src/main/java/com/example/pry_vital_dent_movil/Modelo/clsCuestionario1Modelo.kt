package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsCuestionario1Modelo(private val contexto: Context) {

    fun GuardarDatosPaso1(
        nombre: String, apPaterno: String, apMaterno: String,
        fechaNac: String, sexo: String, telefono: String,
        cp: String, domicilio: String, localidad: String,
        calle: String, referencia: String, alTerminar: (Boolean, String) -> Unit
    ) {
        // Aquí puedes guardar temporalmente en SharedPreferences o base de datos local
        if (nombre.isNotEmpty() && telefono.isNotEmpty()) {
            alTerminar(true, "Datos guardados correctamente")
        } else {
            alTerminar(false, "Faltan campos obligatorios por llenar")
        }
    }
}
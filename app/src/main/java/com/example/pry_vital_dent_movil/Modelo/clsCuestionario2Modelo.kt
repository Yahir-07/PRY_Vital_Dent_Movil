package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsCuestionario2Modelo(private val contexto: Context) {

    fun GuardarDatosClinicos(
        tipoSangre: String, tieneAlergia: Boolean, alergiaDetalle: String,
        ortodoncia: Boolean, extracciones: Boolean, endodoncia: Boolean,
        sangrado: Boolean, bruxismo: Boolean, ninguno: Boolean,
        enfermedades: String, medicamentos: String, motivo: String,
        alTerminar: (Boolean, String) -> Unit
    ) {
        // Aquí puedes guardar temporalmente en preferencias o base de datos local
        if (motivo.isNotEmpty()) {
            alTerminar(true, "Datos clínicos guardados correctamente")
        } else {
            alTerminar(false, "El motivo de consulta es obligatorio")
        }
    }
}
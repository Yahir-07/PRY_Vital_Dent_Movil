package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsCuestionario2Modelo(private val contexto: Context) {

    // Guarda el paso 2 en memoria; se envía al servidor al finalizar el paso 3
    fun GuardarDatosClinicos(
        tipoSangre: String, tieneAlergia: Boolean, alergiaDetalle: String,
        ortodoncia: Boolean, extracciones: Boolean, endodoncia: Boolean,
        sangrado: Boolean, bruxismo: Boolean, ninguno: Boolean,
        enfermedades: String, medicamentos: String, motivo: String,
        alTerminar: (Boolean, String) -> Unit
    ) {
        if (!clsRegistroTemporal.HayCuenta()) {
            alTerminar(false, "Se perdieron los datos de tu cuenta. Inicia sesión para continuar")
            return
        }

        val datos = clsRegistroTemporal
        datos.tipoSangre = tipoSangre
        datos.sangrado = if (sangrado) "si" else "no"
        datos.enfermedades = enfermedades
        datos.medicamentos = medicamentos
        datos.motivoConsulta = motivo

        // La alergia y los antecedentes no tienen columna propia: van a pacientes.observaciones
        val antecedentes = mutableListOf<String>()
        if (ortodoncia) antecedentes.add("ortodoncia previa")
        if (extracciones) antecedentes.add("extracciones")
        if (endodoncia) antecedentes.add("endodoncia")
        if (bruxismo) antecedentes.add("bruxismo")

        val textoAlergia = if (tieneAlergia) "Alergia: $alergiaDetalle" else "Alergias: ninguna"
        val textoAntecedentes = "Antecedentes odontológicos: " +
                if (antecedentes.isEmpty()) "ninguno" else antecedentes.joinToString(", ")

        datos.observaciones = "$textoAlergia\n$textoAntecedentes"

        alTerminar(true, "Datos clínicos guardados correctamente")
    }
}

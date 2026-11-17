package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsCuestionario2Modelo
import com.example.pry_vital_dent_movil.Vista.clsCuestionario2Vista

class clsCuestionario2Presentador(private val vista: clsCuestionario2Vista) {

    private val modelo = clsCuestionario2Modelo(vista)

    fun ClickContinuar(
        tipoSangre: String, tieneAlergia: Boolean, alergiaDetalle: String,
        ortodoncia: Boolean, extracciones: Boolean, endodoncia: Boolean,
        sangrado: Boolean, bruxismo: Boolean, ninguno: Boolean,
        enfermedades: String, medicamentos: String, motivo: String
    ) {
        if (tieneAlergia && alergiaDetalle.isEmpty()) {
            vista.MostrarMensaje("Por favor especifica a qué tienes alergia")
            return
        }

        if (motivo.isEmpty()) {
            vista.MostrarMensaje("Por favor ingresa el motivo de tu consulta")
            return
        }

        modelo.GuardarDatosClinicos(
            tipoSangre, tieneAlergia, alergiaDetalle,
            ortodoncia, extracciones, endodoncia,
            sangrado, bruxismo, ninguno,
            enfermedades, medicamentos, motivo
        ) { exito, mensaje ->
            if (exito) {
                vista.IrAlPasoTres()
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun ClickAtras() {
        vista.RegresarAlPasoUno()
    }
}
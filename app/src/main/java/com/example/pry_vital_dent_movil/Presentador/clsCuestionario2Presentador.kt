package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsCuestionario2Modelo
import com.example.pry_vital_dent_movil.Vista.clsCuestionario2Vista

class clsCuestionario2Presentador(private val vista: clsCuestionario2Vista) {

    private val modelo = clsCuestionario2Modelo(vista)
    fun ClickContinuar(
        tipoSangre: String, respondioAlergia: Boolean, tieneAlergia: Boolean, alergiaDetalle: String,
        ortodoncia: Boolean, extracciones: Boolean, endodoncia: Boolean,
        sangrado: Boolean, bruxismo: Boolean, ninguno: Boolean,
        enfermedades: String, medicamentos: String, motivo: String
    ) {
        vista.LimpiarErrores()
        var valido = true

        if (tipoSangre.isEmpty()) {
            vista.MostrarErrorCampo("tipoSangre", "Selecciona una opción"); valido = false
        }
        if (!respondioAlergia) {
            vista.MostrarMensaje("Indica si tienes alguna alergia"); valido = false
        } else if (tieneAlergia && alergiaDetalle.isEmpty()) {
            vista.MostrarErrorCampo("alergia", "Especifica a qué tienes alergia"); valido = false
        }
        if (motivo.isEmpty()) {
            vista.MostrarErrorCampo("motivo", "Este campo es obligatorio"); valido = false
        }

        if (!valido) {
            if (respondioAlergia) vista.MostrarMensaje("Por favor llena los campos obligatorios (*)")
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

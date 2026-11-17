package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsCuestionario3Modelo(private val contexto: Context) {

    fun FinalizarCuestionario(
        nombreContacto: String, parentesco: String, telefonoContacto: String,
        telefonoAlterno: String, aceptaAviso: Boolean, alTerminar: (Boolean, String) -> Unit
    ) {
        if (nombreContacto.isEmpty() || telefonoContacto.isEmpty()) {
            alTerminar(false, "El nombre y teléfono del contacto son obligatorios")
            return
        }

        if (!aceptaAviso) {
            alTerminar(false, "Debes aceptar el aviso de privacidad para finalizar")
            return
        }

        // Aquí puedes hacer el guardado final o enviar tod0 el registro a tu API de PHP
        alTerminar(true, "¡Cuestionario completado con éxito!")
    }
}
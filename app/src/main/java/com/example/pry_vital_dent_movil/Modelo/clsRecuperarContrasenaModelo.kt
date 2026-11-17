package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsRecuperarContrasenaModelo(private val contexto: Context) {

    fun EnviarEnlaceRecuperacion(correo: String, alTerminar: (Boolean, String) -> Unit) {
        // Aquí harás la llamada POST con Retrofit a tu backend de PHP
        if (correo.isNotEmpty()) {
            alTerminar(true, "Enlace de recuperación enviado con éxito")
        } else {
            alTerminar(false, "Por favor ingresa un correo válido")
        }
    }
}
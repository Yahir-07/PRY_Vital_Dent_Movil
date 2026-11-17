package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsCambiarContrasenaModelo(private val contexto: Context) {

    fun ValidarYActualizar(actual: String, nueva: String, confirmar: String, alTerminar: (Boolean, String) -> Unit) {
        if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            alTerminar(false, "Por favor completa todos los campos")
            return
        }

        if (nueva.length < 8) {
            alTerminar(false, "La contraseña debe tener al menos 8 caracteres")
            return
        }

        if (nueva != confirmar) {
            alTerminar(false, "Las nuevas contraseñas no coinciden")
            return
        }

        if (nueva == actual) {
            alTerminar(false, "La nueva contraseña debe ser distinta a la actual")
            return
        }

        // Simulación de petición exitosa a la API con Retrofit
        alTerminar(true, "Contraseña actualizada exitosamente")
    }
}
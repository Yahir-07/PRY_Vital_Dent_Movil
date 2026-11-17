package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsValidacionBiometricaModelo(private val contexto: Context) {

    private var intentosRestantes = 2

    fun ValidarHuellaDigital(alTerminar: (Boolean, String) -> Unit) {
        // Simulación de autenticación biométrica exitosa o fallida
        val exito = true // Cambiar a false si quieres probar el error de huella

        if (exito) {
            alTerminar(true, "Huella reconocida con éxito")
        } else {
            intentosRestantes--
            if (intentosRestantes > 0) {
                alTerminar(false, "Huella no reconocida. Te quedan $intentosRestantes intentos.")
            } else {
                alTerminar(false, "Demasiados intentos fallidos. Pago cancelado.")
            }
        }
    }
}
package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsSesionVencidaModelo(private val contexto: Context) {

    fun ValidarReingresoHuella(alTerminar: (Boolean, String) -> Unit) {
        // Simulación de autenticación biométrica exitosa tras vencimiento de sesión
        val exito = true
        if (exito) {
            alTerminar(true, "Sesión reanudada con huella")
        } else {
            alTerminar(false, "No se pudo reconocer la huella")
        }
    }
}
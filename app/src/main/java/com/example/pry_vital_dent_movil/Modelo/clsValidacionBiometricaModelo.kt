package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import com.example.pry_vital_dent_movil.R

// PAG-03: la lectura real de la huella la hace Android con clsBiometria (desde la vista).
// Aquí quedan los textos que se le muestran al paciente.
class clsValidacionBiometricaModelo(private val contexto: Context) {

    fun MensajeHuellaReconocida(): String = "Huella reconocida con éxito"

    // "Huella no reconocida. Te quedan 2 intentos."
    fun MensajeIntentoFallido(intentosRestantes: Int): String =
        contexto.resources.getQuantityString(R.plurals.error_huella_intentos, intentosRestantes, intentosRestantes)

    fun MensajeSinIntentos(): String = "Demasiados intentos fallidos. Pago cancelado."

    fun MensajeLectorCancelado(): String = "Toca la huella para intentarlo de nuevo"
}

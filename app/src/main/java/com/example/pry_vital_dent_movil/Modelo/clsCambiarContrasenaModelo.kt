package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsCambiarContrasenaModelo(private val contexto: Context) {

    fun ValidarYActualizar(actual: String, nueva: String, confirmar: String, alTerminar: (Boolean, String) -> Unit) {
        if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            alTerminar(false, "Por favor completa todos los campos")
            return
        }

        val errorContrasena = clsValidarContrasena.Validar(nueva)
        if (errorContrasena != null) {
            alTerminar(false, errorContrasena)
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

        // POST cambiar_contrasena.php: verifica la contraseña actual y guarda la nueva
        val llamada = RetrofitClient.api.CambiarContrasena(
            clsSesion.ObtenerToken(contexto),
            SolicitudCambioContrasena(actual, nueva)
        )

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            alTerminar(exito, mensaje)
        }
    }
}

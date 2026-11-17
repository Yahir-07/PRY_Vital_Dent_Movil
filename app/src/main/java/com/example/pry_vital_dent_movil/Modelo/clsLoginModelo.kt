package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsLoginModelo(private val contexto: Context) {

    fun AutenticarUsuario(usuario: String, contrasena: String, alTerminar: (Boolean, String) -> Unit) {
        // Aquí harás la petición POST con Retrofit a tu backend de PHP
        if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
            // Simulamos éxito
            alTerminar(true, "Bienvenido")
        } else {
            alTerminar(false, "Usuario o contraseña incorrectos")
        }
    }
}
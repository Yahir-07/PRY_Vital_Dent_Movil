package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsRegistroModelo(private val contexto: Context) {

    fun RegistrarUsuario(nombre: String, correo: String, contrasena: String, alTerminar: (Boolean, String) -> Unit) {
        // Aquí harás la llamada a tu API de PHP o backend más adelante.
        // Simulamos un registro exitoso por ahora:
        if (nombre.isNotEmpty() && correo.isNotEmpty() && contrasena.isNotEmpty()) {
            alTerminar(true, "Registro exitoso")
        } else {
            alTerminar(false, "Hay campos vacíos")
        }
    }
}
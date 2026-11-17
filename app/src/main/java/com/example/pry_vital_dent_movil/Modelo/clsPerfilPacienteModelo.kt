package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsPerfilPacienteModelo(private val contexto: Context) {

    fun ObtenerDatosPerfil(alTerminar: (String, String, String, Boolean) -> Unit) {
        // Aquí conectarás tu API con Retrofit para traer la información del usuario autenticado
        val telefono = "771 294 5974"
        val correo = "mariana.lopez@correo.com"
        val domicilio = "Av. Juárez 120, Col. Centro"
        val huellaActiva = true

        alTerminar(telefono, correo, domicilio, huellaActiva)
    }

    fun ActualizarEstadoHuella(activada: Boolean, alTerminar: (Boolean) -> Unit) {
        // Lógica para guardar la preferencia biométrica en SharedPreferences o Backend
        alTerminar(true)
    }
}
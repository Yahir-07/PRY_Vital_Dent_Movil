package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsSesionPorTerminarModelo(private val contexto: Context) {

    fun RenovarSesion(alTerminar: (String) -> Unit) {
        // Lógica para extender el token de sesión con el backend
        alTerminar("Sesión extendida correctamente")
    }

    fun DestruirSesion(alTerminar: () -> Unit) {
        // Lógica para limpiar datos de sesión locales
        alTerminar()
    }
}
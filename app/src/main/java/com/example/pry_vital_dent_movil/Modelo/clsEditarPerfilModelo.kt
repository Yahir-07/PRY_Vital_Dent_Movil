package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsEditarPerfilModelo(private val contexto: Context) {

    // Manda los datos nuevos a actualizar_perfil.php
    fun GuardarContacto(
        telefono: String,
        correo: String,
        domicilio: String,
        alTerminar: (exito: Boolean, mensaje: String) -> Unit
    ) {
        val solicitud = SolicitudActualizarPerfil(telefono, correo, domicilio)
        val llamada = RetrofitClient.api.ActualizarPerfil(clsSesion.ObtenerToken(contexto), solicitud)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            alTerminar(exito, mensaje)
        }
    }
}

// Manda el teléfono, el correo y el domicilio nuevos a actualizar_perfil.php. El código postal se busca en clsCodigoPostalModelo.

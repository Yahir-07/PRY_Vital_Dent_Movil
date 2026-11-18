package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsLoginModelo(private val contexto: Context) {

    // POST login.php: valida el correo y la contraseña contra la tabla usuarios
    fun AutenticarUsuario(usuario: String, contrasena: String, alTerminar: (Boolean, String) -> Unit) {
        val llamada = RetrofitClient.api.IniciarSesion(SolicitudLogin(usuario, contrasena))

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            if (exito && datos != null) {
                clsSesion.Guardar(contexto, datos.token, datos.nombre)
                alTerminar(true, mensaje)
            } else {
                alTerminar(false, mensaje)
            }
        }
    }
}

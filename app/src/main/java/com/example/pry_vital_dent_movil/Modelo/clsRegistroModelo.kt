package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsRegistroModelo(private val contexto: Context) {

    // POST registro.php: crea la cuenta en usuarios y su ficha en pacientes
    fun RegistrarUsuario(nombre: String, correo: String, contrasena: String, alTerminar: (Boolean, String) -> Unit) {
        val llamada = RetrofitClient.api.Registrar(SolicitudRegistro(nombre, correo, contrasena))

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            alTerminar(exito, mensaje)
        }
    }
}

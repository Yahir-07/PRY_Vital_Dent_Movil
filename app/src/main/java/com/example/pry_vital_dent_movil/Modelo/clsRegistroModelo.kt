package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsRegistroModelo(private val contexto: Context) {

    fun RegistrarUsuario(
        nombre: String, correo: String, telefono: String, contrasena: String,
        alTerminar: (Boolean, String) -> Unit
    ) {
        val llamada = RetrofitClient.api.Registrar(SolicitudRegistro(nombre, correo, contrasena, telefono))

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            if (exito) {
                // Se guardan para que guardar_cuestionario.php compruebe la cuenta al finalizar
                clsRegistroTemporal.Iniciar(correo, contrasena)
            }
            alTerminar(exito, mensaje)
        }
    }
}

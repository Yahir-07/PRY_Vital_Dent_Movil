package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

// ACC-04 (RF-04): recuperar la contraseña con un código que llega al correo.
// La API está en APIs-VD (recuperar_contrasena.php) y manda el correo con Brevo.
class clsRecuperarContrasenaModelo(private val contexto: Context) {

    private val url = RetrofitClient.URL_CORREOS + "recuperar_contrasena.php"

    // Paso 1: el servidor manda el código. El mensaje es el mismo exista o no la cuenta.
    fun EnviarCodigo(correo: String, alTerminar: (Boolean, String) -> Unit) {
        val llamada = RetrofitClient.api.RecuperarContrasena(url, "enviar", correo, "", "")
        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            alTerminar(exito, mensaje)
        }
    }

    // Paso 2: con el código correcto se guarda la contraseña nueva
    fun CambiarContrasena(correo: String, codigo: String, nueva: String, alTerminar: (Boolean, String) -> Unit) {
        val llamada = RetrofitClient.api.RecuperarContrasena(url, "cambiar", correo, codigo, nueva)
        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            alTerminar(exito, mensaje)
        }
    }
}

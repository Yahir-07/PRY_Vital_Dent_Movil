package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

// Guarda la sesión del paciente en SharedPreferences (el token que entrega login.php).
object clsSesion {

    private const val PREFERENCIAS = "VitalDentPreferencias"
    private const val CLAVE_TOKEN = "TOKEN_USUARIO"
    private const val CLAVE_NOMBRE = "NOMBRE_USUARIO"
    private const val CLAVE_HUELLA_PAGOS = "HUELLA_PAGOS"

    private fun preferencias(contexto: Context) =
        contexto.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)

    fun Guardar(contexto: Context, token: String, nombre: String) {
        preferencias(contexto).edit()
            .putString(CLAVE_TOKEN, token)
            .putString(CLAVE_NOMBRE, nombre)
            .apply()
    }

    fun ObtenerToken(contexto: Context): String =
        preferencias(contexto).getString(CLAVE_TOKEN, "") ?: ""

    fun ObtenerNombre(contexto: Context): String =
        preferencias(contexto).getString(CLAVE_NOMBRE, "") ?: ""

    fun HuellaPagosActiva(contexto: Context): Boolean =
        preferencias(contexto).getBoolean(CLAVE_HUELLA_PAGOS, false)

    fun GuardarHuellaPagos(contexto: Context, activa: Boolean) {
        preferencias(contexto).edit().putBoolean(CLAVE_HUELLA_PAGOS, activa).apply()
    }

    fun Cerrar(contexto: Context) {
        preferencias(contexto).edit()
            .remove(CLAVE_TOKEN)
            .remove(CLAVE_NOMBRE)
            .apply()
    }
}

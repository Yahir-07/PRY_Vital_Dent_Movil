package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

// Guarda la sesión del paciente en SharedPreferences (el token que entrega login.php).
object clsSesion {

    private const val PREFERENCIAS = "VitalDentPreferencias"
    private const val CLAVE_TOKEN = "TOKEN_USUARIO"
    private const val CLAVE_NOMBRE = "NOMBRE_USUARIO"
    private const val CLAVE_HUELLA_PAGOS = "HUELLA_PAGOS"

    // Acceso con huella en el login. Se guardan aparte para que sigan después de
    // "Cerrar sesión": así el paciente puede volver a entrar solo con su huella.
    private const val CLAVE_HUELLA_LOGIN = "HUELLA_LOGIN"
    private const val CLAVE_HUELLA_TOKEN = "HUELLA_TOKEN"
    private const val CLAVE_HUELLA_NOMBRE = "HUELLA_NOMBRE"
    private const val CLAVE_HUELLA_ID = "HUELLA_ID_PACIENTE"

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

    // ---------- Acceso con huella (login) ----------

    fun HuellaLoginActiva(contexto: Context): Boolean =
        preferencias(contexto).getBoolean(CLAVE_HUELLA_LOGIN, false) && ObtenerTokenHuella(contexto).isNotEmpty()

    fun GuardarHuellaLogin(contexto: Context, token: String, nombre: String, idPaciente: Long) {
        preferencias(contexto).edit()
            .putBoolean(CLAVE_HUELLA_LOGIN, true)
            .putString(CLAVE_HUELLA_TOKEN, token)
            .putString(CLAVE_HUELLA_NOMBRE, nombre)
            .putLong(CLAVE_HUELLA_ID, idPaciente)
            .apply()
    }

    fun ObtenerTokenHuella(contexto: Context): String =
        preferencias(contexto).getString(CLAVE_HUELLA_TOKEN, "") ?: ""

    fun ObtenerNombreHuella(contexto: Context): String =
        preferencias(contexto).getString(CLAVE_HUELLA_NOMBRE, "") ?: ""

    fun ObtenerIdPacienteHuella(contexto: Context): Long =
        preferencias(contexto).getLong(CLAVE_HUELLA_ID, 0L)

    fun DesactivarHuellaLogin(contexto: Context) {
        preferencias(contexto).edit()
            .remove(CLAVE_HUELLA_LOGIN)
            .remove(CLAVE_HUELLA_TOKEN)
            .remove(CLAVE_HUELLA_NOMBRE)
            .remove(CLAVE_HUELLA_ID)
            .apply()
    }

    fun Cerrar(contexto: Context) {
        preferencias(contexto).edit()
            .remove(CLAVE_TOKEN)
            .remove(CLAVE_NOMBRE)
            .apply()
    }
}

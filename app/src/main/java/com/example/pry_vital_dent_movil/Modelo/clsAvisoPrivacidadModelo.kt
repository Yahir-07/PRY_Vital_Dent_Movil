package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class clsAvisoPrivacidadModelo(contexto: Context) {

    private val preferencias = contexto.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)

    fun GuardarAceptacion() {
        val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        preferencias.edit()
            .putBoolean(CLAVE_ACEPTADO, true)
            .putString(CLAVE_VERSION, VERSION_AVISO)
            .putString(CLAVE_FECHA, fecha)
            .apply()
    }

    fun AvisoAceptado(): Boolean =
        preferencias.getBoolean(CLAVE_ACEPTADO, false) &&
            preferencias.getString(CLAVE_VERSION, "") == VERSION_AVISO

    companion object {
        const val VERSION_AVISO = "2026-11"
        private const val PREFERENCIAS = "VitalDentPrivacidad"
        private const val CLAVE_ACEPTADO = "aviso_aceptado"
        private const val CLAVE_VERSION = "aviso_version"
        private const val CLAVE_FECHA = "aviso_fecha"
    }
}

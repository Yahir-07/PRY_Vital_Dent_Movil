package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsBienvenidoModelo(private val contexto: Context) {

    fun ObtenerTokenGuardado(): String {
        val sharedPreferences = contexto.getSharedPreferences("VitalDentPreferencias", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TOKEN_USUARIO", "") ?: ""
    }
}
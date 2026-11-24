package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsBienvenidoModelo(private val contexto: Context) {

    // Token guardado al iniciar sesión (si ya expiró, la primera API que responda 401 regresa al login)
    fun ObtenerTokenGuardado(): String {
        return clsSesion.ObtenerToken(contexto)
    }
    
    fun ObtenerImagenes(alTerminar: (Map<String, String>) -> Unit) {
        clsImagenesModelo.ObtenerImagenes(contexto, alTerminar)
    }
}

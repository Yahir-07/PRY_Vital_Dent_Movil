package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsTipoBracketsModelo(private val contexto: Context) {

    fun ArmarMotivoValoracion(tipoBracket: String): String {
        return "Valoración de $tipoBracket"
    }
    fun ObtenerImagenes(alTerminar: (Map<String, String>) -> Unit) {
        clsImagenesModelo.ObtenerImagenes(contexto, alTerminar)
    }
}
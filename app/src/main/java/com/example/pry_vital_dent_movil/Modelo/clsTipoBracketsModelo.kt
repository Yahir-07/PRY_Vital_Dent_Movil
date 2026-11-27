package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsTipoBracketsModelo(private val contexto: Context) {

    // La valoración se pide agendando una cita normal (agendar_cita.php),
    // aquí solo armamos el motivo. Ej. "Valoración de Brackets Metálicos"
    fun ArmarMotivoValoracion(tipoBracket: String): String {
        return "Valoración de $tipoBracket"
    }
}
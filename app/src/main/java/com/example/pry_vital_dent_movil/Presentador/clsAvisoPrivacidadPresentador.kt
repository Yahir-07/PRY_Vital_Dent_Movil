package com.example.pry_vital_dent_movil.Presentador

import android.content.Context
import com.example.pry_vital_dent_movil.Modelo.clsAvisoPrivacidadModelo
import com.example.pry_vital_dent_movil.Vista.clsAvisoPrivacidadVista

class clsAvisoPrivacidadPresentador(private val vista: clsAvisoPrivacidadVista, contexto: Context) {

    private val modelo = clsAvisoPrivacidadModelo(contexto)

    // El botón Aceptar solo se habilita con la casilla de la burbuja marcada
    fun CasillaCambiada(marcada: Boolean) {
        vista.HabilitarAceptar(marcada)
    }

    fun ClickAceptar() {
        modelo.GuardarAceptacion()
        vista.Cerrar()
        vista.NotificarAceptado()
    }

    fun ClickCerrar() {
        vista.Cerrar()
    }
}

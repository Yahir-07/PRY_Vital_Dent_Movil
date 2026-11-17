package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsContactoModelo
import com.example.pry_vital_dent_movil.Vista.clsContactoVista

class clsContactoPresentador(private val vista: clsContactoVista) {

    private val modelo = clsContactoModelo(vista)

    fun CargarInformacion() {
        modelo.ObtenerDatosContactoHorario { direccion, telefono, sem, sab, dom ->
            vista.MostrarInformacion(direccion, telefono, sem, sab, dom)
        }
    }

    fun ClickComoLlegar() {
        vista.AbrirMapaComoLlegar()
    }

    fun ClickLlamar() {
        vista.RealizarLlamadaConsultorio()
    }
}
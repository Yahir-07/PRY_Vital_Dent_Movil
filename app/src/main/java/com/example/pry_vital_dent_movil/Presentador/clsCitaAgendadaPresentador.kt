package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsCitaAgendadaModelo
import com.example.pry_vital_dent_movil.Vista.clsCitaAgendadaVista

class clsCitaAgendadaPresentador(private val vista: clsCitaAgendadaVista) {

    private val modelo = clsCitaAgendadaModelo(vista)

    fun CargarResumenCita() {
        modelo.ObtenerDetallesCitaReciente { fecha, hora, motivo, doctor, estado ->
            vista.MostrarResumen(fecha, hora, motivo, doctor, estado)
        }
    }

    fun ClickVerMisCitas() {
        vista.IrAMisCitas()
    }

    fun ClickVolverInicio() {
        vista.IrAlInicio()
    }
}
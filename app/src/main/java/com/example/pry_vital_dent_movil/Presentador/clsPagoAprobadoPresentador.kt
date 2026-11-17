package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsPagoAprobadoModelo
import com.example.pry_vital_dent_movil.Vista.clsPagoAprobadoVista

class clsPagoAprobadoPresentador(private val vista: clsPagoAprobadoVista) {

    private val modelo = clsPagoAprobadoModelo(vista)

    fun CargarComprobante() {
        modelo.ObtenerDetallesComprobante { monto, concepto, fecha, medio, autorizado, folio ->
            vista.MostrarDatosComprobante(monto, concepto, fecha, medio, autorizado, folio)
        }
    }

    fun ClickVerMisPagos() {
        vista.IrAMisPagos()
    }

    fun ClickVolverInicio() {
        vista.IrAInicio()
    }
}
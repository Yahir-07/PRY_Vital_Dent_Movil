package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsPagoAprobadoModelo
import com.example.pry_vital_dent_movil.Vista.clsPagoAprobadoVista

class clsPagoAprobadoPresentador(private val vista: clsPagoAprobadoVista) {

    private val modelo = clsPagoAprobadoModelo(vista)

    // PAG-02: confirmación con fecha y monto, tomada del pago registrado en la base de datos
    fun CargarComprobante(referencia: String) {
        if (referencia.isEmpty()) {
            vista.MostrarMensaje("No se encontró el pago")
            return
        }
        modelo.ObtenerDetallesComprobante(referencia) { exito, mensaje, comprobante ->
            if (exito && comprobante != null) {
                vista.MostrarDatosComprobante(
                    comprobante.monto, comprobante.concepto, comprobante.fecha,
                    comprobante.medio, comprobante.autorizado, comprobante.folio
                )
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun ClickVerMisPagos() {
        vista.IrAMisPagos()
    }

    fun ClickVolverInicio() {
        vista.IrAInicio()
    }
}

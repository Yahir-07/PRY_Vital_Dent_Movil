package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsRealizarPagoModelo
import com.example.pry_vital_dent_movil.Vista.clsRealizarPagoVista

class clsRealizarPagoPresentador(private val vista: clsRealizarPagoVista) {

    private val modelo = clsRealizarPagoModelo(vista)

    fun CargarDatosPago() {
        modelo.ObtenerDetallesPago { concepto, total ->
            vista.MostrarDetalles(concepto, total)
        }
    }

    fun ClickPagar(esTarjetaGuardada: Boolean) {
        val medio = if (esTarjetaGuardada) "Visa •••• 4621" else "Nueva tarjeta"
        modelo.ProcesarPago(medio) { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                vista.IrAValidacionBiometrica() // Aquí el presentador le ordena a la vista dar el siguiente paso
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }
}
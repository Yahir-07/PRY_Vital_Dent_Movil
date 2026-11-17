package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsValidacionBiometricaModelo
import com.example.pry_vital_dent_movil.Vista.clsValidacionBiometricaVista

class clsValidacionBiometricaPresentador(private val vista: clsValidacionBiometricaVista) {

    private val modelo = clsValidacionBiometricaModelo(vista)

    fun ClickEscanearHuella() {
        modelo.ValidarHuellaDigital { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                vista.IrAPagoAprobado() // Pantalla 16: Pago aprobado (Éxito)
            } else {
                vista.MostrarError(mensaje)
            }
        }
    }

    fun ClickCancelarPago() {
        vista.CancelarYRegresar()
    }
}
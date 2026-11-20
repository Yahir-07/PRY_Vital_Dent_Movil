package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsValidacionBiometricaModelo
import com.example.pry_vital_dent_movil.Vista.clsValidacionBiometricaVista

class clsValidacionBiometricaPresentador(private val vista: clsValidacionBiometricaVista) {

    private val modelo = clsValidacionBiometricaModelo(vista)

    // Al abrir la pantalla se abre el lector de huella
    fun AlIniciar() {
        vista.SolicitarHuella(modelo.MensajeSinIntentos())
    }

    // Tocar el ícono de la huella vuelve a abrir el lector
    fun ClickEscanearHuella() {
        vista.SolicitarHuella(modelo.MensajeSinIntentos())
    }

    fun HuellaReconocida() {
        vista.MostrarMensaje(modelo.MensajeHuellaReconocida())
        vista.IrAPagoAprobado() // Realizar pago continúa con Mercado Pago
    }

    fun IntentoFallido(intentosRestantes: Int) {
        vista.MostrarError(modelo.MensajeIntentoFallido(intentosRestantes))
    }

    // Demasiados intentos, lector bloqueado o celular sin huella: se cancela el pago
    fun HuellaNoValidada(mensaje: String) {
        vista.MostrarError(mensaje)
        vista.CancelarYRegresar()
    }

    // El paciente cerró el lector: se queda en la pantalla para reintentar o cancelar
    fun LectorCancelado() {
        vista.MostrarAviso(modelo.MensajeLectorCancelado())
    }

    fun ClickCancelarPago() {
        vista.CancelarYRegresar()
    }
}

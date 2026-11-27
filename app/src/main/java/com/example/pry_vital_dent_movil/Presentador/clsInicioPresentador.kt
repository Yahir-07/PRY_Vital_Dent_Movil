package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsInicioModelo
import com.example.pry_vital_dent_movil.Vista.clsInicioVista
import com.example.pry_vital_dent_movil.Modelo.clsNotificacionesModelo

class clsInicioPresentador(private val vista: clsInicioVista) {

    private val modelo = clsInicioModelo(vista)

    fun InicializarPanel() {
        vista.MostrarDatosUsuario(modelo.ObtenerNombreGuardado())

        modelo.CargarInformacionPanel(
            alTerminar = { nombre, citaTitulo, citaDetalle, tratamientoNombre, pagosTexto ->
                vista.MostrarDatosUsuario(nombre)
                vista.MostrarDatosProximaCita(citaTitulo, citaDetalle)
                vista.MostrarDatosTratamiento(tratamientoNombre)
                vista.MostrarPagosPendientes(pagosTexto)
                vista.OcultarAviso()
            },
            alFallar = { mensaje ->
                vista.MostrarAvisoError(mensaje)
            }
        )

        clsNotificacionesModelo.RegistrarCelular(vista)
    }

    fun ClickProximaCita() {
        vista.IrADetalleCita()
    }

    fun ClickTratamientoActivo() {
        vista.IrADetalleTratamiento()
    }

    fun ClickAgendarCita() {
        vista.IrAAgendarCita()
    }

    fun ClickTratamientos() {
        vista.IrAListaTratamientos()
    }

    fun ClickHistorialMedico() {
        vista.IrAHistorialMedico()
    }

    fun ClickPagos() {
        vista.IrAPagos()
    }

    fun ClickMultimedia() {
        vista.IrAMultimedia()
    }
    
    fun clickNavCitas() {
        vista.IrAMisCitas()
    }

    fun clickNavTratamientos() {
        vista.IrAListaTratamientos()
    }

    fun clickNavPagos() {
        vista.IrAPagos()
    }

    fun clickNavPerfil() {
        vista.IrAPerfil()
    }
}
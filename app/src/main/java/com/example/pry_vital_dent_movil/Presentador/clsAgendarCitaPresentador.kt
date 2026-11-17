package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsAgendarCitaModelo
import com.example.pry_vital_dent_movil.Vista.clsAgendarCitaVista

class clsAgendarCitaPresentador(private val vista: clsAgendarCitaVista) {

    private val modelo = clsAgendarCitaModelo(vista)
    private var fechaSeleccionada: String = ""
    private var horaSeleccionada: String = ""

    fun SeleccionarFecha(fecha: String) {
        fechaSeleccionada = fecha
        modelo.ObtenerHorariosDisponibles(fecha) { listaHoras ->
            vista.MostrarHorariosDisponibles(listaHoras)
        }
    }

    fun SeleccionarHora(hora: String) {
        horaSeleccionada = hora
        vista.MostrarMensaje("Hora seleccionada: $hora")
    }

    fun ClickConfirmarCita(motivo: String) {
        if (fechaSeleccionada.isEmpty()) {
            vista.MostrarMensaje("Selecciona una fecha en el calendario")
            return
        }

        if (horaSeleccionada.isEmpty()) {
            vista.MostrarMensaje("Selecciona un horario disponible")
            return
        }

        if (motivo.isEmpty()) {
            vista.MostrarMensaje("Escribe el motivo de la cita")
            return
        }

        modelo.RegistrarCita(fechaSeleccionada, horaSeleccionada, motivo) { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                vista.IrACofirmacionCita()
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }
}
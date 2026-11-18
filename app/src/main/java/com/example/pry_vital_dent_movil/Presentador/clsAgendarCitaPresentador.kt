package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.DataHora
import com.example.pry_vital_dent_movil.Modelo.clsAgendarCitaModelo
import com.example.pry_vital_dent_movil.Vista.clsAgendarCitaVista

class clsAgendarCitaPresentador(private val vista: clsAgendarCitaVista) {

    private val modelo = clsAgendarCitaModelo(vista)
    private var fechaSeleccionada: String = ""
    private var horaSeleccionada: String = ""

    fun SeleccionarFecha(fecha: String) {
        fechaSeleccionada = fecha
        horaSeleccionada = "" // al cambiar de día hay que volver a elegir hora
        modelo.ObtenerHorariosDisponibles(fecha) { exito, mensaje, listaHoras ->
            vista.MostrarHorariosDisponibles(fecha, listaHoras)
            if (!exito || listaHoras.isEmpty()) {
                vista.MostrarMensaje(mensaje)
            } else if (listaHoras.none { it.disponible }) {
                vista.MostrarMensaje("Ya no hay horarios libres ese día, elige otra fecha")
            }
        }
    }

    fun SeleccionarHora(hora: DataHora) {
        horaSeleccionada = hora.valor
        vista.MostrarMensaje("Hora seleccionada: ${hora.texto}")
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
            vista.MostrarMensaje(mensaje)
            if (exito) {
                vista.IrACofirmacionCita()
            } else {
                // Puede que alguien más ganó el horario: recargamos las horas del día
                SeleccionarFecha(fechaSeleccionada)
            }
        }
    }
}
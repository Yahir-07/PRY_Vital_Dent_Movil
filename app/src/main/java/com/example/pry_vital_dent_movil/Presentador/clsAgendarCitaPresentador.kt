package com.example.pry_vital_dent_movil.Presentador

import android.content.Context
import com.example.pry_vital_dent_movil.Modelo.DataHora
import com.example.pry_vital_dent_movil.Modelo.clsAgendarCitaModelo
import com.example.pry_vital_dent_movil.Vista.clsAgendarCitaVista

class clsAgendarCitaPresentador(private val vista: clsAgendarCitaVista) {

    private val modelo = clsAgendarCitaModelo(vista as Context)

    private var fechaSeleccionada: String = ""
    private var horaSeleccionada: String = ""
    private var idDoctor: String = "" // vacío = cualquier doctor libre

    // Cuando se llega desde el perfil de un doctor ("Agendar con este doctor")
    fun AsignarDoctor(idDoctorElegido: String) {
        idDoctor = idDoctorElegido
    }

    fun SeleccionarFecha(fecha: String) {
        fechaSeleccionada = fecha
        horaSeleccionada = "" // al cambiar de día hay que volver a elegir hora
        vista.MostrarFechaSeleccionada(fecha)

        modelo.ObtenerHorasDisponibles(fecha, idDoctor) { exito, mensaje, listaHoras ->
            vista.MostrarHorariosDisponibles(listaHoras)
            if (!exito || listaHoras.isEmpty()) {
                vista.MostrarMensaje(mensaje) // ej. "El consultorio está cerrado ese día"
            } else if (listaHoras.none { it.isDisponible }) {
                vista.MostrarMensaje("Ya no hay horarios libres ese día, elige otra fecha")
            }
        }
    }

    fun SeleccionarHora(hora: DataHora) {
        if (hora.isDisponible) {
            // "valor" es la hora en formato 24 h ("16:00") que espera la API
            horaSeleccionada = hora.valor.ifEmpty { hora.hora }
            vista.MostrarMensaje("Hora seleccionada: ${hora.hora}")
        } else {
            vista.MostrarMensaje("Este horario no está disponible")
        }
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
            vista.MostrarMensaje("Elige o escribe el motivo de la cita")
            return
        }

        modelo.RegistrarCita(fechaSeleccionada, horaSeleccionada, motivo, idDoctor) { exito, mensaje ->
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

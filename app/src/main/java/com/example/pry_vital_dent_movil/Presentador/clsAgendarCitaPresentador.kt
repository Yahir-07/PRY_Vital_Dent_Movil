package com.example.pry_vital_dent_movil.Presentador

import android.content.Context
import com.example.pry_vital_dent_movil.Modelo.DataHora
import com.example.pry_vital_dent_movil.Modelo.clsAgendarCitaModelo
import com.example.pry_vital_dent_movil.Vista.clsAgendarCitaVista

class clsAgendarCitaPresentador(private val vista: clsAgendarCitaVista) {

    private val modelo = clsAgendarCitaModelo(vista as Context)

    private var fechaSeleccionada: String = ""
    private var horaSeleccionada: String = ""

    fun SeleccionarFecha(fecha: String) {
        fechaSeleccionada = fecha

        // Usamos el métod0 de tu modelo que ya regresa directamente los objetos DataHora
        modelo.ObtenerHorasDisponibles { listaHoras ->
            vista.MostrarHorariosDisponibles(listaHoras)
        }
    }

    fun SeleccionarHora(hora: DataHora) {
        if (hora.isDisponible) {
            horaSeleccionada = hora.hora
            vista.MostrarMensaje("Hora seleccionada: ${hora.hora}")
        } else {
            vista.MostrarMensaje("Este horario no está disponible")
        }
    }

    fun ClickConfirmarCita(motivo: String) {
        // Aquí le ordenas al modelo que registre la cita usando los datos que ya tiene el presentador
        modelo.RegistrarCita(fechaSeleccionada, horaSeleccionada, motivo) { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                vista.IrACofirmacionCita() // O el nombre de la función que tengas en tu vista para avanzar
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }
}
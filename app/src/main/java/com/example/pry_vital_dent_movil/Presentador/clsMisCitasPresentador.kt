package com.example.pry_vital_dent_movil.Presentador

import android.content.Context
import com.example.pry_vital_dent_movil.Modelo.clsMisCitasModelo
import com.example.pry_vital_dent_movil.Vista.clsMisCitasVista

class clsMisCitasPresentador(private val vista: clsMisCitasVista) {

    private val modelo = clsMisCitasModelo(vista as Context)

    fun CargarCitasProximas() {
        CargarCitas("proximas")
    }

    fun CargarCitasAnteriores() {
        CargarCitas("anteriores")
    }

    private fun CargarCitas(tipo: String) {
        modelo.ObtenerListaCitas(tipo) { exito, mensaje, lista ->
            vista.MostrarListaCitas(lista)
            if (!exito) {
                vista.MostrarMensaje(mensaje)
            } else if (lista.isEmpty()) {
                vista.MostrarMensaje(if (tipo == "proximas") "No tienes citas próximas" else "No tienes citas anteriores")
            }
        }
    }

    fun ClickAgendarNuevaCita() {
        vista.IrAAgendarCita()
    }
}
package com.example.pry_vital_dent_movil.Presentador

import android.content.Context
import com.example.pry_vital_dent_movil.Modelo.clsMisCitasModelo
import com.example.pry_vital_dent_movil.Vista.clsMisCitasVista

class clsMisCitasPresentador(private val vista: clsMisCitasVista) {

    private val modelo = clsMisCitasModelo(vista as Context)

    fun CargarCitasProximas() {
        modelo.ObtenerListaCitas { lista ->
            vista.MostrarListaCitas(lista)
        }
    }

    fun CargarCitasAnteriores() {
        // Aquí puedes cargar las citas pasadas o la lista correspondiente
        modelo.ObtenerListaCitas { lista ->
            vista.MostrarListaCitas(lista)
        }
    }

    fun ClickAgendarNuevaCita() {
        vista.IrAAgendarCita()
    }
}
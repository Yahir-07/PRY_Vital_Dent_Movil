package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsMisTratamientosModelo
import com.example.pry_vital_dent_movil.Vista.clsMisTratamientosVista

class clsMisTratamientosPresentador(private val vista: clsMisTratamientosVista) {

    private val modelo = clsMisTratamientosModelo(vista)

    fun CargarTratamientosEnCurso() {
        modelo.ObtenerTratamientosEnCurso { lista ->
            vista.MostrarListaTratamientos(lista)
            vista.MostrarAviso("Toca un tratamiento para ver su avance sesión por sesión.")
        }
    }

    fun CargarTratamientosFinalizados() {
        modelo.ObtenerTratamientosFinalizados { lista ->
            vista.MostrarListaTratamientos(lista)
            vista.MostrarAviso("Tratamientos completados con éxito.")
        }
    }

    fun ClickTratamiento(nombreTratamiento: String) {
        vista.IrADetalleTratamiento(nombreTratamiento)
    }
}
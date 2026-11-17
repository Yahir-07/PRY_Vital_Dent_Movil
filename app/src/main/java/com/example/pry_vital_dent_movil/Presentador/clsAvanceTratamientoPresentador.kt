package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsAvanceTratamientoModelo
import com.example.pry_vital_dent_movil.Vista.clsAvanceTratamientoVista

class clsAvanceTratamientoPresentador(private val vista: clsAvanceTratamientoVista) {

    private val modelo = clsAvanceTratamientoModelo(vista)

    fun CargarDatosDetalle() {
        modelo.ObtenerResumenTratamiento { porcentaje, sesiones, finEstimado, estado ->
            vista.MostrarResumen(porcentaje, sesiones, finEstimado, estado)
        }

        modelo.ObtenerHistorialAvances { listaAvances ->
            vista.MostrarListaAvances(listaAvances)
        }
    }
}
package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.DataAvance
import com.example.pry_vital_dent_movil.Modelo.clsAvanceTratamientoModelo
import com.example.pry_vital_dent_movil.Vista.clsAvanceTratamientoVista

class clsAvanceTratamientoPresentador(private val vista: clsAvanceTratamientoVista) {

    private val modelo = clsAvanceTratamientoModelo(vista)

    // idTratamiento vacío = el tratamiento en curso más reciente
    fun CargarDatosDetalle(idTratamiento: String) {
        modelo.ObtenerDetalleTratamiento(idTratamiento) { exito, mensaje, datos ->
            if (exito && datos != null) {
                vista.MostrarNombreTratamiento(datos.nombre)
                vista.MostrarResumen(datos.porcentaje, datos.sesiones, datos.finEstimado, datos.estado)
                vista.MostrarListaAvances(datos.avances ?: emptyList())
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun SeleccionarAvance(avance: DataAvance) {
        vista.MostrarMensaje("Seleccionaste: ${avance.sesion} - ${avance.fecha}")
    }
}

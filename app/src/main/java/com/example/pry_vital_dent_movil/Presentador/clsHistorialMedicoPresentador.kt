package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.DataHistorial
import com.example.pry_vital_dent_movil.Modelo.clsHistorialMedicoModelo
import com.example.pry_vital_dent_movil.Vista.clsHistorialMedicoVista

class clsHistorialMedicoPresentador(private val vista: clsHistorialMedicoVista) {

    private val modelo = clsHistorialMedicoModelo(vista)

    fun CargarDatosIniciales() {
        modelo.ObtenerAlergias { alergias ->
            vista.MostrarAlergias(alergias)
        }
        FiltrarHistorial("Todo")
    }

    fun FiltrarHistorial(filtro: String) {
        modelo.ObtenerHistorial(filtro) { lista ->
            vista.MostrarListaHistorial(lista)
        }
    }

    fun SeleccionarHistorial(historial: DataHistorial) {
        vista.MostrarMensaje("Seleccionaste: ${historial.titulo}")
    }
}
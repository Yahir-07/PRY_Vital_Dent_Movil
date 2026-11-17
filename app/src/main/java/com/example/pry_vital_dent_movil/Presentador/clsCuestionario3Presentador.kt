package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsCuestionario3Modelo
import com.example.pry_vital_dent_movil.Vista.clsCuestionario3Vista

class clsCuestionario3Presentador(private val vista: clsCuestionario3Vista) {

    private val modelo = clsCuestionario3Modelo(vista)

    fun ClickFinalizar(
        nombreContacto: String, parentesco: String, telefonoContacto: String,
        telefonoAlterno: String, aceptaAviso: Boolean
    ) {
        modelo.FinalizarCuestionario(nombreContacto, parentesco, telefonoContacto, telefonoAlterno, aceptaAviso) { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                vista.IrAlPanelPrincipal()
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun ClickAtras() {
        vista.VolverAlPasoDos()
    }
}
package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsCambiarContrasenaModelo
import com.example.pry_vital_dent_movil.Vista.clsCambiarContrasenaVista

class clsCambiarContrasenaPresentador(private val vista: clsCambiarContrasenaVista) {

    private val modelo = clsCambiarContrasenaModelo(vista)

    fun ClickGuardarCambios(actual: String, nueva: String, confirmar: String) {
        modelo.ValidarYActualizar(actual, nueva, confirmar) { exito, mensaje ->
            vista.MostrarMensaje(mensaje)
            if (exito) {
                vista.RegresarAlPerfil()
            }
        }
    }

    fun ClickCancelar() {
        vista.RegresarAlPerfil()
    }
}
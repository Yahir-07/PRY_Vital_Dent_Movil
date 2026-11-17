package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsRecuperarContrasenaModelo
import com.example.pry_vital_dent_movil.Vista.clsRecuperarContrasenaVista

class clsRecuperarContrasenaPresentador(private val vista: clsRecuperarContrasenaVista) {

    private val modelo = clsRecuperarContrasenaModelo(vista)

    fun ClickEnviarEnlace(correo: String) {
        if (correo.isEmpty()) {
            vista.MostrarMensaje("El campo de correo no puede estar vacío")
            return
        }

        modelo.EnviarEnlaceRecuperacion(correo) { exito, mensaje ->
            if (exito) {
                vista.MostrarAvisoEnviado(mensaje)
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun ClickVolverLogin() {
        vista.VolverAlLogin()
    }
}
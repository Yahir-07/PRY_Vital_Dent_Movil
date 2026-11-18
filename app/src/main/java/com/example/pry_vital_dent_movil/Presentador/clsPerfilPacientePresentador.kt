package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsPerfilPacienteModelo
import com.example.pry_vital_dent_movil.Vista.clsPerfilPacienteVista

class clsPerfilPacientePresentador(private val vista: clsPerfilPacienteVista) {

    private val modelo = clsPerfilPacienteModelo(vista)

    fun CargarPerfil() {
        modelo.ObtenerDatosPerfil { datos, mensaje, huellaActiva ->
            if (datos != null) {
                vista.MostrarInformacion(datos.telefono, datos.correo, datos.domicilio, huellaActiva)
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun CambiarEstadoHuella(activada: Boolean) {
        modelo.ActualizarEstadoHuella(activada) { exito ->
            if (exito) {
                vista.MostrarMensaje(if (activada) "Huella para pagos activada" else "Huella para pagos desactivada")
            }
        }
    }

    fun ClickCambiarContrasena() {
        vista.IrACambiarContrasena() // Pantalla 18
    }

    fun ClickNuestrosDoctores() {
        vista.IrANuestrosDoctores() // Pantalla 20
    }

    fun ClickContactoHorario() {
        vista.IrAContactoHorario() // Pantalla 22
    }

    fun ClickCerrarSesion() {
        modelo.CerrarSesion()
        vista.CerrarSesionApp()
    }
}
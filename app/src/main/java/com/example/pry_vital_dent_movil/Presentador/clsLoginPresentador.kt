package com.example.pry_vital_dent_movil.Presentador

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.pry_vital_dent_movil.Modelo.clsLoginModelo
import com.example.pry_vital_dent_movil.Vista.clsLoginVista
import com.example.pry_vital_dent_movil.Vista.clsRecuperarContrasenaVista

class clsLoginPresentador(private val vista: clsLoginVista) {

    private val modelo = clsLoginModelo(vista)

    fun ClickIniciarSesion(usuario: String, contrasena: String) {
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            vista.MostrarError("Por favor llena todos los campos")
            return
        }

        vista.OcultarError()

        modelo.AutenticarUsuario(usuario, contrasena) { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                vista.IrAlPanelPrincipal()
            } else {
                vista.MostrarError(mensaje)
            }
        }
    }

    fun ClickOlvidasteContrasena() {
        vista.IrARecuperarContrasena()
    }

    fun ClickIngresarHuella() {
        vista.MostrarMensaje("Autenticación por huella próximamente")
    }

    fun ClickIrCrearCuenta() {
        vista.IrARegistro()
    }
}
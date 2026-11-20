package com.example.pry_vital_dent_movil.Presentador

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.pry_vital_dent_movil.Modelo.clsLoginModelo
import com.example.pry_vital_dent_movil.Vista.clsLoginVista
import com.example.pry_vital_dent_movil.Vista.clsRecuperarContrasenaVista

class clsLoginPresentador(private val vista: clsLoginVista) {

    private val modelo = clsLoginModelo(vista)

    // Al abrir la pantalla: si el paciente ya activó su huella, se le pide de una vez
    fun AlIniciar() {
        if (modelo.HuellaActiva() && vista.BiometriaDisponible()) {
            vista.SolicitarHuella(modelo.NombreHuella())
        }
    }

    fun ClickIniciarSesion(usuario: String, contrasena: String) {
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            vista.MostrarError("Por favor llena todos los campos")
            return
        }

        vista.OcultarError()

        modelo.AutenticarUsuario(usuario, contrasena) { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                // Primera vez con contraseña: se ofrece usar la huella la próxima vez
                if (!modelo.HuellaActiva() && vista.BiometriaDisponible()) {
                    vista.PreguntarActivarHuella()
                } else {
                    vista.IrAlPanelPrincipal()
                }
            } else {
                vista.MostrarError(mensaje)
            }
        }
    }

    fun ClickOlvidasteContrasena() {
        vista.IrARecuperarContrasena()
    }

    fun ClickIngresarHuella() {
        vista.OcultarError()
        when {
            !vista.BiometriaDisponible() ->
                vista.MostrarError(vista.MotivoBiometriaNoDisponible())
            !modelo.HuellaActiva() ->
                vista.MostrarError("Primero inicia sesión con tu correo y contraseña. Después podrás entrar con tu huella.")
            else ->
                vista.SolicitarHuella(modelo.NombreHuella())
        }
    }

    // La vista avisa que Android reconoció la huella o el rostro
    fun HuellaReconocida() {
        modelo.IniciarSesionConHuella { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                vista.IrAlPanelPrincipal()
            } else {
                vista.MostrarError(mensaje)
            }
        }
    }

    fun HuellaNoReconocida(mensaje: String) {
        vista.MostrarError(mensaje)
    }

    // El paciente aceptó y confirmó su huella después del primer login
    fun HuellaActivada() {
        modelo.ActivarHuella()
        vista.MostrarMensaje("Acceso con huella activado")
        vista.IrAlPanelPrincipal()
    }

    fun ActivacionHuellaOmitida() {
        vista.IrAlPanelPrincipal()
    }

    fun ClickIrCrearCuenta() {
        vista.IrARegistro()
    }
}
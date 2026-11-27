package com.example.pry_vital_dent_movil.Presentador

import android.util.Patterns
import com.example.pry_vital_dent_movil.Modelo.clsRecuperarContrasenaModelo
import com.example.pry_vital_dent_movil.Modelo.clsValidarContrasena
import com.example.pry_vital_dent_movil.Vista.clsRecuperarContrasenaVista

class clsRecuperarContrasenaPresentador(private val vista: clsRecuperarContrasenaVista) {

    private val modelo = clsRecuperarContrasenaModelo(vista)

    // Correo al que se mandó el código (el paso 2 usa este mismo)
    private var correoEnviado = ""
    private var procesando = false

    // Paso 1: pedir el código
    fun ClickEnviarCodigo(correo: String) {
        if (procesando) return
        if (correo.isEmpty()) {
            vista.MostrarMensaje("El campo de correo no puede estar vacío")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            vista.MostrarMensaje("Escribe un correo válido")
            return
        }

        procesando = true
        vista.MostrarProcesando(true)
        modelo.EnviarCodigo(correo) { exito, mensaje ->
            procesando = false
            vista.MostrarProcesando(false)
            if (exito) {
                correoEnviado = correo
                vista.MostrarAvisoEnviado(mensaje)
                vista.MostrarPasoCodigo()
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun ClickReenviarCodigo() {
        ClickEnviarCodigo(correoEnviado)
    }

    // Paso 2: código + contraseña nueva
    fun ClickCambiarContrasena(codigo: String, nueva: String, confirmar: String) {
        if (procesando) return
        if (correoEnviado.isEmpty()) {
            vista.MostrarMensaje("Primero escribe tu correo y pide el código")
            return
        }
        if (!Regex("\\d{6}").matches(codigo)) {
            vista.MostrarMensaje("El código tiene 6 números")
            return
        }
        val errorContrasena = clsValidarContrasena.Validar(nueva)
        if (errorContrasena != null) {
            vista.MostrarMensaje(errorContrasena)
            return
        }
        if (nueva != confirmar) {
            vista.MostrarMensaje("Las contraseñas no coinciden")
            return
        }

        procesando = true
        vista.MostrarProcesando(true)
        modelo.CambiarContrasena(correoEnviado, codigo, nueva) { exito, mensaje ->
            procesando = false
            vista.MostrarProcesando(false)
            vista.MostrarMensaje(mensaje)
            if (exito) {
                vista.VolverAlLogin()
            }
        }
    }

    fun ClickVolverLogin() {
        vista.VolverAlLogin()
    }
}

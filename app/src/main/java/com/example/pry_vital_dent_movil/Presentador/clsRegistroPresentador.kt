package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsValidarContrasena
import com.example.pry_vital_dent_movil.Modelo.clsRegistroModelo
import com.example.pry_vital_dent_movil.Vista.clsRegistroVista

class clsRegistroPresentador(private val actividad: clsRegistroVista) {

    private val modelo = clsRegistroModelo(actividad)

    fun ClickCrearCuenta(
        nombre: String, correo: String, telefono: String,
        contrasena: String, confirmarContrasena: String, aceptaAviso: Boolean
    ) {
        actividad.LimpiarErrores()
        var valido = true

        if (nombre.isEmpty()) {
            actividad.MostrarErrorCampo("nombre", "Este campo es obligatorio"); valido = false
        }
        if (correo.isEmpty()) {
            actividad.MostrarErrorCampo("correo", "Este campo es obligatorio"); valido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            actividad.MostrarErrorCampo("correo", "Ingresa un correo válido"); valido = false
        }
        if (telefono.isEmpty()) {
            actividad.MostrarErrorCampo("telefono", "Este campo es obligatorio"); valido = false
        } else if (!Regex("^\\d{10}$").matches(telefono)) {
            actividad.MostrarErrorCampo("telefono", "Ingresa un teléfono de 10 dígitos"); valido = false
        }
        if (contrasena.isEmpty()) {
            actividad.MostrarErrorCampo("contrasena", "Este campo es obligatorio"); valido = false
        } else if (clsValidarContrasena.Validar(contrasena) != null) {
            actividad.MostrarErrorCampo("contrasena", clsValidarContrasena.Validar(contrasena)!!); valido = false
        }
        if (confirmarContrasena.isEmpty()) {
            actividad.MostrarErrorCampo("confirmar", "Este campo es obligatorio"); valido = false
        } else if (contrasena != confirmarContrasena) {
            actividad.MostrarErrorCampo("confirmar", "Las contraseñas no coinciden"); valido = false
        }

        if (!valido) {
            actividad.MostrarMensaje("Por favor llena todos los campos obligatorios")
            return
        }

        if (!aceptaAviso) {
            actividad.MostrarMensaje("Debes aceptar el aviso de privacidad")
            return
        }

        actividad.HabilitarBoton(false)
        modelo.RegistrarUsuario(nombre, correo, telefono, contrasena) { exito, mensaje ->
            actividad.HabilitarBoton(true)
            if (exito) {
                actividad.MostrarMensaje("¡Cuenta creada! Ahora completa tus datos")
                actividad.IrAlCuestionario(nombre, telefono)
            } else {
                actividad.MostrarMensaje(mensaje)
            }
        }
    }

    fun ClickIrLogin() {
        actividad.IrAlLogin()
    }
}
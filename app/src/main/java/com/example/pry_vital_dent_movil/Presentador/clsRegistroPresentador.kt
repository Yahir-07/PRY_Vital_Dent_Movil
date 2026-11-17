package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsRegistroModelo
import com.example.pry_vital_dent_movil.Vista.clsRegistroVista

class clsRegistroPresentador(private val actividad: clsRegistroVista) {

    private val modelo = clsRegistroModelo(actividad)

    fun ClickCrearCuenta(nombre: String, correo: String, contrasena: String, confirmarContrasena: String, aceptaAviso: Boolean) {
        // Validaciones básicas de lógica
        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            actividad.MostrarMensaje("Por favor llena todos los campos obligatorios")
            return
        }

        if (contrasena != confirmarContrasena) {
            actividad.MostrarMensaje("Las contraseñas no coinciden")
            return
        }

        if (!aceptaAviso) {
            actividad.MostrarMensaje("Debes aceptar el aviso de privacidad")
            return
        }

        // Si pasa las validaciones, mandamos los datos al modelo
        modelo.RegistrarUsuario(nombre, correo, contrasena) { exito, mensaje ->
            if (exito) {
                actividad.MostrarMensaje("¡Cuenta creada con éxito!")
                actividad.IrAlLogin()
            } else {
                actividad.MostrarMensaje(mensaje)
            }
        }
    }

    fun ClickIrLogin() {
        actividad.IrAlLogin()
    }
}
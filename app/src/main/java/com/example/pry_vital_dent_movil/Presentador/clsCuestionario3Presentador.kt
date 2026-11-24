package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsCuestionario3Modelo
import com.example.pry_vital_dent_movil.Vista.clsCuestionario3Vista

class clsCuestionario3Presentador(private val vista: clsCuestionario3Vista) {

    private val modelo = clsCuestionario3Modelo(vista)

    fun ClickFinalizar(
        nombreContacto: String, parentesco: String, telefonoContacto: String,
        telefonoAlterno: String, aceptaAviso: Boolean
    ) {
        vista.LimpiarErrores()
        var valido = true

        if (nombreContacto.isEmpty()) {
            vista.MostrarErrorCampo("nombre", "Este campo es obligatorio"); valido = false
        }
        if (parentesco.isEmpty()) {
            vista.MostrarErrorCampo("parentesco", "Selecciona una opción"); valido = false
        }
        if (telefonoContacto.isEmpty()) {
            vista.MostrarErrorCampo("telefono", "Este campo es obligatorio"); valido = false
        } else if (!Regex("^\\d{10}$").matches(telefonoContacto)) {
            vista.MostrarErrorCampo("telefono", "Ingresa un teléfono de 10 dígitos"); valido = false
        }
        if (telefonoAlterno.isNotEmpty() && !Regex("^\\d{10}$").matches(telefonoAlterno)) {
            vista.MostrarErrorCampo("telefonoAlterno", "Ingresa un teléfono de 10 dígitos"); valido = false
        }

        if (!valido) {
            vista.MostrarMensaje("Por favor llena los campos obligatorios (*)")
            return
        }

        modelo.FinalizarCuestionario(nombreContacto, parentesco, telefonoContacto, telefonoAlterno, aceptaAviso) { exito, mensaje ->
            vista.MostrarMensaje(mensaje)
            if (exito) {
                vista.IrAlLogin()
            }
        }
    }

    fun ClickAtras() {
        vista.VolverAlPasoDos()
    }
}
package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsCuestionario1Modelo
import com.example.pry_vital_dent_movil.Vista.clsCuestionario1Vista

class clsCuestionario1Presentador(private val vista: clsCuestionario1Vista) {

    private val modelo = clsCuestionario1Modelo(vista)

    fun ClickContinuar(
        nombre: String, apPaterno: String, apMaterno: String,
        fechaNac: String, sexo: String, telefono: String,
        cp: String, domicilio: String, localidad: String,
        calle: String, referencia: String
    ) {
        vista.LimpiarErrores()
        var valido = true

        if (nombre.isEmpty()) {
            vista.MostrarErrorCampo("nombre", "Este campo es obligatorio"); valido = false
        }
        if (apPaterno.isEmpty()) {
            vista.MostrarErrorCampo("apPaterno", "Este campo es obligatorio"); valido = false
        }
        if (fechaNac.isEmpty()) {
            vista.MostrarErrorCampo("fechaNac", "Este campo es obligatorio"); valido = false
        }
        if (sexo.isEmpty()) {
            vista.MostrarErrorCampo("sexo", "Selecciona una opción"); valido = false
        }
        if (telefono.isEmpty()) {
            vista.MostrarErrorCampo("telefono", "Este campo es obligatorio"); valido = false
        } else if (!Regex("^\\d{10}$").matches(telefono)) {
            vista.MostrarErrorCampo("telefono", "Ingresa un teléfono de 10 dígitos"); valido = false
        }

        if (!valido) {
            vista.MostrarMensaje("Por favor llena los campos obligatorios (*)")
            return
        }

        modelo.GuardarDatosPaso1(
            nombre, apPaterno, apMaterno, fechaNac, sexo,
            telefono, cp, domicilio, localidad, calle, referencia
        ) { exito, mensaje ->
            if (exito) {
                vista.IrAlPasoDos()
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }
}
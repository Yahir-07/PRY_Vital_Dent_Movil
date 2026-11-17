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
        if (nombre.isEmpty() || telefono.isEmpty() || cp.isEmpty()) {
            vista.MostrarMensaje("Por favor llena los campos obligatorios principales (Nombre, Teléfono, CP)")
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
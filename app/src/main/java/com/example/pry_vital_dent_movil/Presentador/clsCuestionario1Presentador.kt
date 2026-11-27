package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsCodigoPostalModelo
import com.example.pry_vital_dent_movil.Modelo.clsCuestionario1Modelo
import com.example.pry_vital_dent_movil.Vista.clsCuestionario1Vista

class clsCuestionario1Presentador(private val vista: clsCuestionario1Vista) {

    private val modelo = clsCuestionario1Modelo(vista)
    private val codigoPostal = clsCodigoPostalModelo()

    // El último CP que sí encontró COPOMEX
    private var cpEncontrado = ""

    // Cuando el CP tiene 5 números lo busca. Si el paciente cambia un CP que ya se había
    // encontrado, se borra lo que se llenó solo. Todo es opcional, si falla puede escribirlo a mano.
    fun CodigoPostalCambio(cp: String) {
        if (cpEncontrado != "" && cp != cpEncontrado) {
            cpEncontrado = ""
            vista.LimpiarDatosDomicilio()
        }

        if (cp.length != 5) return

        vista.MostrarBuscandoCodigoPostal(true)
        codigoPostal.BuscarCodigoPostal(cp) { datos, mensaje ->
            vista.MostrarBuscandoCodigoPostal(false)

            // Si ya escribió otro CP mientras buscaba, esta respuesta ya no sirve
            if (vista.CodigoPostalEscrito() != cp) return@BuscarCodigoPostal

            if (datos == null) {
                vista.MostrarErrorCodigoPostal(mensaje)
                return@BuscarCodigoPostal
            }

            cpEncontrado = cp
            vista.MostrarDatosCodigoPostal("${datos.municipio}, ${datos.estado}", datos.colonias)
        }
    }

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
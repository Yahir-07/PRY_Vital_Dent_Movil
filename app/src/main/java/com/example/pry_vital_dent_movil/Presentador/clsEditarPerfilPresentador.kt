package com.example.pry_vital_dent_movil.Presentador

import android.util.Patterns
import com.example.pry_vital_dent_movil.Modelo.clsCodigoPostalModelo
import com.example.pry_vital_dent_movil.Modelo.clsEditarPerfilModelo
import com.example.pry_vital_dent_movil.Vista.clsEditarPerfilVista

class clsEditarPerfilPresentador(private val vista: clsEditarPerfilVista) {

    private val modelo = clsEditarPerfilModelo(vista)
    private val codigoPostal = clsCodigoPostalModelo()

    // Domicilio que ya tenía, se usa si no escribe uno nuevo
    private var domicilioActual = ""

    // Lo que regresó SEPOMEX del último CP
    private var cpEncontrado = ""
    private var municipio = ""
    private var estado = ""

    private var guardando = false

    fun MostrarDatosActuales(telefono: String, correo: String, domicilio: String) {
        domicilioActual = domicilio
        vista.MostrarDatosActuales(telefono, correo, domicilio)
    }

    // Cada vez que cambia el CP
    fun CodigoPostalCambio(cp: String) {
        // Si cambió el CP se borra lo de antes
        if (cp != cpEncontrado) {
            cpEncontrado = ""
            municipio = ""
            estado = ""
            vista.LimpiarDatosDomicilio()
        }

        // Se busca hasta que tenga los 5 números
        if (cp.length == 5) {
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
                municipio = datos.municipio
                estado = datos.estado
                vista.MostrarDatosCodigoPostal(datos.municipio, datos.estado, datos.colonias)
            }
        }
    }

    fun ClickGuardarCambios(telefono: String, correo: String, cp: String, colonia: String, calle: String) {
        if (guardando) return
        vista.LimpiarErrores()

        // Mismas validaciones que tiene actualizar_perfil.php
        if (telefono.length != 10 || !telefono.all { it.isDigit() }) {
            vista.MostrarErrorTelefono("El teléfono debe tener 10 números")
            return
        }
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            vista.MostrarErrorCorreo("Escribe un correo válido")
            return
        }

        // Si no llenó nada del domicilio se deja el que ya tenía
        val domicilio: String
        val noCambioDomicilio = cp.isEmpty() && colonia.isEmpty() && calle.isEmpty()

        if (noCambioDomicilio) {
            domicilio = if (domicilioActual == "Sin domicilio registrado") "" else domicilioActual
        } else {
            if (cp.length != 5 || cp != cpEncontrado) {
                vista.MostrarErrorCodigoPostal("Escribe un código postal válido de 5 números")
                return
            }
            if (colonia.isEmpty()) {
                vista.MostrarErrorColonia("Elige tu colonia")
                return
            }
            if (calle.isEmpty()) {
                vista.MostrarErrorCalle("Escribe tu calle y número")
                return
            }
            domicilio = ArmarDomicilio(calle, colonia, cp)
        }

        // Guardar en el servidor
        guardando = true
        vista.MostrarGuardando(true)
        modelo.GuardarContacto(telefono, correo.lowercase(), domicilio) { exito, mensaje ->
            guardando = false
            vista.MostrarGuardando(false)

            if (exito) {
                vista.MostrarDatosGuardados("Tus datos se guardaron")
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    // En la BD el domicilio es un solo campo de 200, por eso se junta todo
    private fun ArmarDomicilio(calle: String, colonia: String, cp: String): String {
        val partes = listOf(calle, "Col. $colonia", municipio, estado, "C.P. $cp")
        return partes.filter { it.isNotBlank() }.joinToString(", ").take(200)
    }

    fun ClickAceptarGuardado() {
        vista.RegresarAlPerfil()
    }

    fun ClickCancelar() {
        vista.RegresarAlPerfil()
    }
}

// Lógica: cuando el CP tiene 5 números lo busca, y al guardar revisa el teléfono, el correo y el
// domicilio, se los manda al modelo y si todo sale bien regresa a Mi perfil.

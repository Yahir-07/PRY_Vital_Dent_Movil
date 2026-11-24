package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsCuestionario3Modelo(private val contexto: Context) {

    private var enviando = false

    // POST guardar_cuestionario.php: manda los 3 pasos juntos a la ficha del paciente
    fun FinalizarCuestionario(
        nombreContacto: String, parentesco: String, telefonoContacto: String,
        telefonoAlterno: String, aceptaAviso: Boolean, alTerminar: (Boolean, String) -> Unit
    ) {
        if (nombreContacto.isEmpty() || telefonoContacto.isEmpty()) {
            alTerminar(false, "El nombre y teléfono del contacto son obligatorios")
            return
        }

        if (!aceptaAviso) {
            alTerminar(false, "Debes aceptar el aviso de privacidad para finalizar")
            return
        }

        if (!clsRegistroTemporal.HayCuenta()) {
            alTerminar(false, "Se perdieron los datos de tu cuenta. Inicia sesión para continuar")
            return
        }

        // Evita mandar dos veces si se toca Finalizar seguido
        if (enviando) return
        enviando = true

        val datos = clsRegistroTemporal
        val solicitud = SolicitudCuestionario(
            correo = datos.correo,
            contrasena = datos.contrasena,
            nombre = datos.nombre,
            telefono = datos.telefono,
            fechaNacimiento = datos.fechaNacimiento,
            sexo = datos.sexo,
            domicilio = datos.domicilio,
            tipoSangre = datos.tipoSangre,
            sangrado = datos.sangrado,
            enfermedades = datos.enfermedades,
            medicamentos = datos.medicamentos,
            motivoConsulta = datos.motivoConsulta,
            observaciones = datos.observaciones,
            contactoEmergencia = nombreContacto,
            parentesco = parentesco,
            telEmergencia = telefonoContacto,
            telEmergenciaAlterno = telefonoAlterno
        )

        RetrofitClient.Ejecutar(contexto, RetrofitClient.api.GuardarCuestionario(solicitud)) { exito, mensaje, _ ->
            enviando = false
            if (exito) {
                datos.Limpiar()
                alTerminar(true, "¡Registro completado! Ya puedes iniciar sesión")
            } else {
                alTerminar(false, mensaje)
            }
        }
    }
}
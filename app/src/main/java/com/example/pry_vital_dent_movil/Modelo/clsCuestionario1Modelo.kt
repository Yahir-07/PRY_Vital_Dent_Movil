package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class clsCuestionario1Modelo(private val contexto: Context) {

    // Guarda el paso 1 en memoria; se envía al servidor al finalizar el paso 3
    fun GuardarDatosPaso1(
        nombre: String, apPaterno: String, apMaterno: String,
        fechaNac: String, sexo: String, telefono: String,
        cp: String, domicilio: String, localidad: String,
        calle: String, referencia: String, alTerminar: (Boolean, String) -> Unit
    ) {
        if (!clsRegistroTemporal.HayCuenta()) {
            alTerminar(false, "Se perdieron los datos de tu cuenta. Inicia sesión para continuar")
            return
        }

        val fechaIso = ConvertirFecha(fechaNac)
        if (fechaIso == null) {
            alTerminar(false, "La fecha de nacimiento no es válida")
            return
        }

        val datos = clsRegistroTemporal
        // pacientes.nombre es una sola columna: nombre(s) + apellidos
        datos.nombre = listOf(nombre, apPaterno, apMaterno).filter { it.isNotEmpty() }.joinToString(" ")
        datos.telefono = telefono
        datos.fechaNacimiento = fechaIso
        // pacientes.sexo es enum('F','M','otro'); "Prefiero no decir" se guarda vacío (NULL)
        datos.sexo = when (sexo) {
            "Mujer" -> "F"
            "Hombre" -> "M"
            else -> ""
        }
        // pacientes.domicilio es una sola columna de 200 caracteres
        datos.domicilio = listOf(
            calle,
            domicilio,
            localidad,
            if (cp.isNotEmpty()) "C.P. $cp" else "",
            if (referencia.isNotEmpty()) "Ref.: $referencia" else ""
        ).filter { it.isNotEmpty() }.joinToString(", ").take(200)

        alTerminar(true, "Datos guardados correctamente")
    }

    // "dd/MM/yyyy" (como lo muestra el calendario) -> "yyyy-MM-dd" (como lo guarda MySQL)
    private fun ConvertirFecha(texto: String): String? {
        val entrada = SimpleDateFormat("dd/MM/yyyy", Locale.US).apply { isLenient = false }
        val salida = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return try {
            entrada.parse(texto)?.let { salida.format(it) }
        } catch (e: ParseException) {
            null
        }
    }
}

package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class clsEditarPerfilModelo(private val contexto: Context) {

    // Token de COPOMEX, es el mismo que usa la web
    private val TOKEN_COPOMEX = "7c8cf8c2-b3c6-4e43-9c46-c42f28c419c3"
    private val URL_COPOMEX = "https://api.copomex.com/query/info_cp/"

    // Trae municipio, estado y colonias del CP
    fun BuscarCodigoPostal(cp: String, alTerminar: (datos: DatosCodigoPostal?, mensaje: String) -> Unit) {
        val llamada = RetrofitClient.api.ConsultarCodigoPostal(URL_COPOMEX + cp, "simplified", TOKEN_COPOMEX)

        // COPOMEX no responde con exito/mensaje/datos, por eso aquí no uso Ejecutar
        llamada.enqueue(object : Callback<RespuestaCopomex> {

            override fun onResponse(call: Call<RespuestaCopomex>, response: Response<RespuestaCopomex>) {
                val cuerpo = response.body()
                val datos = cuerpo?.response

                if (!response.isSuccessful || cuerpo == null || cuerpo.error || datos == null) {
                    alTerminar(null, "No encontramos ese código postal. Revísalo.")
                    return
                }

                val colonias = LeerColonias(datos.asentamiento)
                if (colonias.isEmpty()) {
                    alTerminar(null, "No encontramos colonias para ese código postal.")
                    return
                }

                val resultado = DatosCodigoPostal(
                    municipio = datos.municipio ?: "",
                    estado = datos.estado ?: "",
                    colonias = colonias
                )
                alTerminar(resultado, "")
            }

            override fun onFailure(call: Call<RespuestaCopomex>, t: Throwable) {
                alTerminar(null, "No se pudo buscar el código postal. Revisa tu conexión a internet.")
            }
        })
    }

    // Si solo hay una colonia COPOMEX la manda como texto y no como lista
    private fun LeerColonias(asentamiento: Any?): List<String> {
        val colonias = mutableListOf<String>()

        if (asentamiento is List<*>) {
            for (colonia in asentamiento) {
                val texto = colonia.toString().trim()
                if (texto.isNotEmpty()) colonias.add(texto)
            }
        } else if (asentamiento is String && asentamiento.isNotBlank()) {
            colonias.add(asentamiento.trim())
        }

        return colonias
    }

    // Manda los datos nuevos a actualizar_perfil.php
    fun GuardarContacto(
        telefono: String,
        correo: String,
        domicilio: String,
        alTerminar: (exito: Boolean, mensaje: String) -> Unit
    ) {
        val solicitud = SolicitudActualizarPerfil(telefono, correo, domicilio)
        val llamada = RetrofitClient.api.ActualizarPerfil(clsSesion.ObtenerToken(contexto), solicitud)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            alTerminar(exito, mensaje)
        }
    }
}

// Lógica: busca en COPOMEX el municipio, el estado y las colonias del código postal, y manda
// el teléfono, el correo y el domicilio nuevos a actualizar_perfil.php.

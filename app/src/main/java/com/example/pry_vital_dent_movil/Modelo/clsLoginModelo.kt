package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class clsLoginModelo(private val contexto: Context) {

    private var ultimoLogin: DatosLogin? = null

    fun AutenticarUsuario(usuario: String, contrasena: String, alTerminar: (Boolean, String) -> Unit) {
        val llamada = RetrofitClient.api.IniciarSesion(SolicitudLogin(usuario, contrasena))

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            if (exito && datos != null) {
                clsSesion.Guardar(contexto, datos.token, datos.nombre)
                ultimoLogin = datos
                ActualizarHuellaTrasLogin(datos)
                alTerminar(true, mensaje)
            } else {
                alTerminar(false, mensaje)
            }
        }
    }


    fun HuellaActiva(): Boolean = clsSesion.HuellaLoginActiva(contexto)

    fun NombreHuella(): String = clsSesion.ObtenerNombreHuella(contexto)

    fun ActivarHuella() {
        val datos = ultimoLogin ?: return
        clsSesion.GuardarHuellaLogin(contexto, datos.token, datos.nombre, datos.idPaciente)
    }

    private fun ActualizarHuellaTrasLogin(datos: DatosLogin) {
        if (!clsSesion.HuellaLoginActiva(contexto)) return
        if (clsSesion.ObtenerIdPacienteHuella(contexto) == datos.idPaciente) {
            clsSesion.GuardarHuellaLogin(contexto, datos.token, datos.nombre, datos.idPaciente)
        } else {
            clsSesion.DesactivarHuellaLogin(contexto)
        }
    }

    fun IniciarSesionConHuella(alTerminar: (Boolean, String) -> Unit) {
        val token = clsSesion.ObtenerTokenHuella(contexto)
        val nombre = clsSesion.ObtenerNombreHuella(contexto)

        if (token.isEmpty()) {
            alTerminar(false, "Primero inicia sesión con tu correo y contraseña.")
            return
        }

        RetrofitClient.api.ObtenerPerfil(token).enqueue(object : Callback<RespuestaApi<DatosPerfil>> {

            override fun onResponse(
                call: Call<RespuestaApi<DatosPerfil>>,
                response: Response<RespuestaApi<DatosPerfil>>
            ) {
                val cuerpo = response.body()
                when {
                    response.isSuccessful && cuerpo != null && cuerpo.exito -> {
                        clsSesion.Guardar(contexto, token, nombre)
                        alTerminar(true, "Bienvenido de nuevo, $nombre")
                    }
                    response.code() == 401 -> {
                        clsSesion.DesactivarHuellaLogin(contexto)
                        alTerminar(false, "Tu acceso con huella venció. Inicia sesión con tu correo y contraseña para activarlo de nuevo.")
                    }
                    else -> alTerminar(false, cuerpo?.mensaje ?: "Error del servidor (${response.code()})")
                }
            }

            override fun onFailure(call: Call<RespuestaApi<DatosPerfil>>, t: Throwable) {
                alTerminar(false, "No se pudo conectar con el servidor. Revisa tu conexión.")
            }
        })
    }
}

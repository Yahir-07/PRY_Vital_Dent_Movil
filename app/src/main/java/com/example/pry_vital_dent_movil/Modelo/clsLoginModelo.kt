package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class clsLoginModelo(private val contexto: Context) {

    // Datos del último login con contraseña (para activar la huella después)
    private var ultimoLogin: DatosLogin? = null

    // POST login.php: valida el correo y la contraseña contra la tabla usuarios
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

    // ---------- Acceso con huella ----------

    fun HuellaActiva(): Boolean = clsSesion.HuellaLoginActiva(contexto)

    fun NombreHuella(): String = clsSesion.ObtenerNombreHuella(contexto)

    // El paciente aceptó usar su huella después de entrar con contraseña
    fun ActivarHuella() {
        val datos = ultimoLogin ?: return
        clsSesion.GuardarHuellaLogin(contexto, datos.token, datos.nombre, datos.idPaciente)
    }

    // Si ya tenía la huella activada, se guarda el token nuevo;
    // si entró otra persona en este celular, se apaga la huella de la anterior.
    private fun ActualizarHuellaTrasLogin(datos: DatosLogin) {
        if (!clsSesion.HuellaLoginActiva(contexto)) return
        if (clsSesion.ObtenerIdPacienteHuella(contexto) == datos.idPaciente) {
            clsSesion.GuardarHuellaLogin(contexto, datos.token, datos.nombre, datos.idPaciente)
        } else {
            clsSesion.DesactivarHuellaLogin(contexto)
        }
    }

    // Android ya reconoció la huella: se revisa con perfil.php que el token guardado
    // siga vigente en el servidor y, si es así, se abre la sesión con ese token.
    fun IniciarSesionConHuella(alTerminar: (Boolean, String) -> Unit) {
        val token = clsSesion.ObtenerTokenHuella(contexto)
        val nombre = clsSesion.ObtenerNombreHuella(contexto)

        if (token.isEmpty()) {
            alTerminar(false, "Primero inicia sesión con tu correo y contraseña.")
            return
        }

        // Se usa enqueue directo (no RetrofitClient.Ejecutar) para que un token vencido
        // solo apague la huella y no mande al usuario a otra pantalla.
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

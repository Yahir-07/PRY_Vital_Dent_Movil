package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object clsNotificacionesModelo {

    private const val ETIQUETA = "NotificacionesVitalDent"

    private const val ACCION_REGISTRAR = "registrar"
    private const val ACCION_DESACTIVAR = "desactivar"

    fun RegistrarCelular(contexto: Context) {
        val tokenSesion = clsSesion.ObtenerToken(contexto)
        if (tokenSesion.isEmpty()) return

        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { tokenFcm ->
                Log.i(ETIQUETA, "Token FCM: $tokenFcm")
                Enviar(tokenSesion, tokenFcm, ACCION_REGISTRAR)
            }
            .addOnFailureListener { error ->
                Log.w(ETIQUETA, "No se pudo obtener el token de Firebase", error)
            }
    }

    fun RegistrarTokenNuevo(contexto: Context, tokenFcm: String) {
        val tokenSesion = clsSesion.ObtenerToken(contexto)
        if (tokenSesion.isEmpty()) {
            Log.i(ETIQUETA, "Token nuevo sin sesión iniciada; se registrará al entrar al inicio")
            return
        }
        Enviar(tokenSesion, tokenFcm, ACCION_REGISTRAR)
    }

    fun DesactivarCelular(contexto: Context) {
        val tokenSesion = clsSesion.ObtenerToken(contexto) // se lee ahora, antes de que se borre
        if (tokenSesion.isEmpty()) return

        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { tokenFcm -> Enviar(tokenSesion, tokenFcm, ACCION_DESACTIVAR) }
            .addOnFailureListener { error ->
                Log.w(ETIQUETA, "No se pudo obtener el token de Firebase para desactivarlo", error)
            }
    }

    private fun Enviar(tokenSesion: String, tokenFcm: String, accion: String) {
        val llamada = RetrofitClient.api.GuardarTokenFcm(tokenSesion, tokenFcm, NombreDispositivo(), accion)

        llamada.enqueue(object : Callback<RespuestaApi<Any>> {
            override fun onResponse(call: Call<RespuestaApi<Any>>, response: Response<RespuestaApi<Any>>) {
                val cuerpo = response.body()
                if (response.isSuccessful && cuerpo != null && cuerpo.exito) {
                    Log.i(ETIQUETA, "Token '$accion': ${cuerpo.mensaje}")
                } else {
                    Log.w(ETIQUETA, "Token '$accion' falló (${response.code()}): " +
                            (cuerpo?.mensaje ?: response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<RespuestaApi<Any>>, t: Throwable) {
                Log.w(ETIQUETA, "No se pudo conectar para el token '$accion'", t)
            }
        })
    }

    private fun NombreDispositivo(): String {
        val marca = Build.MANUFACTURER.replaceFirstChar { it.uppercase() }
        return if (Build.MODEL.startsWith(Build.MANUFACTURER, ignoreCase = true)) Build.MODEL
        else "$marca ${Build.MODEL}"
    }
}
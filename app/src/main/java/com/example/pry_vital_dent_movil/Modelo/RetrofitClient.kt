package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.pry_vital_dent_movil.InterfaceAPI.ApiService
import com.example.pry_vital_dent_movil.Vista.clsLoginVista
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // Carpeta de las APIs dentro de htdocs (C:\xampp\htdocs\APIS_proyecto).
    // Se usa la IP de la PC en la red WiFi para que funcione en el celular y en el emulador.
    // · Si cambias de red, la IP cambia: revísala con "ipconfig" (Dirección IPv4) y actualízala aquí.
    // · Solo emulador: también sirve "http://10.0.2.2/APIS_proyecto/"
    const val URL_BASE = "http://172.26.72.181/APIS_proyecto/"

    // Encabezado donde viaja el token de sesión
    const val ENCABEZADO_TOKEN = "X-Auth-Token"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    /**
     * Ejecuta una llamada en segundo plano y regresa el resultado en el hilo principal.
     * alTerminar(exito, mensaje, datos)
     * Si el servidor responde 401 a una petición con token, se cierra la sesión y se manda al login.
     */
    fun <T> Ejecutar(
        contexto: Context,
        llamada: Call<RespuestaApi<T>>,
        alTerminar: (exito: Boolean, mensaje: String, datos: T?) -> Unit
    ) {
        llamada.enqueue(object : Callback<RespuestaApi<T>> {

            override fun onResponse(call: Call<RespuestaApi<T>>, response: Response<RespuestaApi<T>>) {
                val cuerpo = response.body()
                if (response.isSuccessful && cuerpo != null) {
                    alTerminar(cuerpo.exito, cuerpo.mensaje ?: "", cuerpo.datos)
                    return
                }

                val mensaje = LeerMensajeError(response.errorBody()?.string())
                    ?: "Error del servidor (${response.code()})"

                if (response.code() == 401 && call.request().header(ENCABEZADO_TOKEN) != null) {
                    SesionExpirada(contexto, mensaje)
                    return
                }

                alTerminar(false, mensaje, null)
            }

            override fun onFailure(call: Call<RespuestaApi<T>>, t: Throwable) {
                alTerminar(
                    false,
                    "No se pudo conectar con el servidor. Revisa tu conexión y que XAMPP esté encendido.",
                    null
                )
            }
        })
    }

    private fun LeerMensajeError(texto: String?): String? {
        if (texto.isNullOrBlank()) return null
        return try {
            val json = JsonParser.parseString(texto).asJsonObject
            when {
                json.has("mensaje") && !json.get("mensaje").isJsonNull -> json.get("mensaje").asString
                json.has("error") && !json.get("error").isJsonNull -> json.get("error").asString
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun SesionExpirada(contexto: Context, mensaje: String) {
        clsSesion.Cerrar(contexto)
        Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show()
        val intent = Intent(contexto, clsLoginVista::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        contexto.startActivity(intent)
    }
}

package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.pry_vital_dent_movil.InterfaceAPI.ApiService
import com.example.pry_vital_dent_movil.Vista.clsLoginVista
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // APIs publicadas en el hosting (Hostinger): funcionan desde cualquier red, con HTTPS
    const val URL_HOSTING = "https://victor.4buthh.com/APIS_proyecto/"

    // true = usar el XAMPP de tu PC (C:\xampp\htdocs\APIS_proyecto) para hacer pruebas locales
    const val USAR_SERVIDOR_LOCAL = false

    val URL_BASE: String = if (USAR_SERVIDOR_LOCAL) "http://192.168.137.1/APIS_proyecto/" else URL_HOSTING

    // APIs de correos de citas con Brevo (carpeta APIs-VD). Cambia la ruta si se sube en otra carpeta.
    val URL_CORREOS: String =
        if (USAR_SERVIDOR_LOCAL) "http://192.168.137.1/APIs-VD/" else "https://victor.4buthh.com/APIs-VD/"

    // Solo en modo local: direcciones donde puede estar la PC con XAMPP.
    // Se prueban en orden y se recuerda la que funcionó.
    // · 192.168.137.1 = la PC cuando comparte su "Zona con cobertura inalámbrica" (siempre es la misma)
    // · 10.177.56.181 / 172.26.72.181 = IP de la PC en el WiFi (cambia con la red: revísala con "ipconfig")
    // · 10.0.2.2 = la PC vista desde el emulador de Android Studio
    private val SERVIDORES = listOf("192.168.137.1", "10.177.56.181", "172.26.72.181", "10.0.2.2")

    @Volatile
    private var servidorActivo: String = SERVIDORES.first()

    // Encabezado donde viaja el token de sesión
    const val ENCABEZADO_TOKEN = "X-Auth-Token"

    // Si no se puede conectar a una dirección, prueba la siguiente de SERVIDORES
    private val buscarServidor = Interceptor { chain ->
        val original = chain.request()
        val orden = listOf(servidorActivo) + SERVIDORES.filter { it != servidorActivo }
        var ultimoError: IOException? = null

        for (servidor in orden) {
            val peticion = original.newBuilder()
                .url(original.url.newBuilder().host(servidor).build())
                .build()
            try {
                val respuesta = chain.proceed(peticion)
                servidorActivo = servidor
                return@Interceptor respuesta
            } catch (e: IOException) {
                ultimoError = e // no respondió: se intenta con la siguiente dirección
            }
        }
        throw ultimoError ?: IOException("No se encontró el servidor")
    }

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .apply { if (USAR_SERVIDOR_LOCAL) addInterceptor(buscarServidor) }
        .connectTimeout(if (USAR_SERVIDOR_LOCAL) 4L else 15L, TimeUnit.SECONDS)
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
                    "No se pudo conectar con el servidor.",
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

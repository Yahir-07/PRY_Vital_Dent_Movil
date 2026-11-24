package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
object clsImagenesModelo {

    const val LOGO = "logo_vitaldent"
    const val FOTO_BIENVENIDA = "consultorio_3"
    const val BRACKETS_METALICOS = "brackets_metalicos"
    const val BRACKETS_CERAMICOS = "brackets_ceramicos"
    const val BRACKETS_ZAFIRO = "brackets_zafiro"
    const val BRACKETS_AUTOLIGABLES = "brackets_autoligables"

    private const val PREFERENCIAS = "VitalDentImagenes"
    private const val CLAVE_LISTA = "LISTA_IMAGENES"

    private var enMemoria: Map<String, String>? = null
    private var actualizadaEnEstaSesion = false

    private val enEspera = mutableListOf<(Map<String, String>) -> Unit>()

    fun ObtenerImagenes(contexto: Context, alTerminar: (Map<String, String>) -> Unit) {
        val app = contexto.applicationContext

        // 1. Ya está en memoria (otra pantalla la pidió antes)
        enMemoria?.let {
            alTerminar(it)
            ActualizarEnSegundoPlano(app)
            return
        }

        // 2. Hay una copia guardada en el teléfono: se usa de inmediato y se actualiza en segundo plano
        val guardada = LeerGuardada(app)
        if (guardada.isNotEmpty()) {
            enMemoria = guardada
            alTerminar(guardada)
            ActualizarEnSegundoPlano(app)
            return
        }

        // 3. Primera vez: hay que esperar a la API
        enEspera.add(alTerminar)
        Descargar(app)
    }

    // Refresca la lista una vez por cada vez que se abre la app (por si cambió alguna imagen)
    private fun ActualizarEnSegundoPlano(app: Context) {
        if (!actualizadaEnEstaSesion) Descargar(app)
    }

    // GET imagenes.php (todas las secciones en una sola llamada)
    private var descargando = false

    private fun Descargar(app: Context) {
        if (descargando) return
        descargando = true

        RetrofitClient.Ejecutar(app, RetrofitClient.api.ObtenerImagenes(null)) { exito, _, datos ->
            descargando = false
            if (exito && !datos.isNullOrEmpty()) {
                enMemoria = datos
                actualizadaEnEstaSesion = true
                Guardar(app, datos)
            }
            val pendientes = enEspera.toList()
            enEspera.clear()
            pendientes.forEach { it(enMemoria ?: emptyMap()) }
        }
    }

    private fun preferencias(contexto: Context) =
        contexto.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)

    private fun Guardar(contexto: Context, mapa: Map<String, String>) {
        preferencias(contexto).edit().putString(CLAVE_LISTA, Gson().toJson(mapa)).apply()
    }

    private fun LeerGuardada(contexto: Context): Map<String, String> {
        val json = preferencias(contexto).getString(CLAVE_LISTA, null) ?: return emptyMap()
        return try {
            val tipo = object : TypeToken<Map<String, String>>() {}.type
            Gson().fromJson<Map<String, String>>(json, tipo) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }
}

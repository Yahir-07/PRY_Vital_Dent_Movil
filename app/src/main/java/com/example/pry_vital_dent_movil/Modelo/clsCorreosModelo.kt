package com.example.pry_vital_dent_movil.Modelo

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Correos de citas con Brevo (APIs-VD/enviar_correo_cita.php).
// El correo se manda "por detrás": si falla, la cita igual queda agendada
// y al paciente no se le muestra ningún error.
object clsCorreosModelo {

    private const val ETIQUETA = "CorreosVitalDent"

    const val TIPO_REGISTRADA = "registrada"
    const val TIPO_CONFIRMADA = "confirmada"
    const val TIPO_REPROGRAMADA = "reprogramada"
    const val TIPO_CANCELADA = "cancelada"

    fun EnviarCorreoCita(idCita: String, tipo: String, alTerminar: (Boolean, String) -> Unit = { _, _ -> }) {
        if (idCita.isEmpty()) {
            alTerminar(false, "Falta el id de la cita")
            return
        }

        val llamada = RetrofitClient.api.EnviarCorreoCita(
            RetrofitClient.URL_CORREOS + "enviar_correo_cita.php", idCita, tipo
        )

        llamada.enqueue(object : Callback<RespuestaApi<Any>> {
            override fun onResponse(call: Call<RespuestaApi<Any>>, response: Response<RespuestaApi<Any>>) {
                val cuerpo = response.body()
                val exito = response.isSuccessful && cuerpo != null && cuerpo.exito
                val mensaje = cuerpo?.mensaje ?: "Error del servidor de correos (${response.code()})"
                Log.d(ETIQUETA, "Correo '$tipo' de la cita $idCita: $mensaje")
                alTerminar(exito, mensaje)
            }

            override fun onFailure(call: Call<RespuestaApi<Any>>, t: Throwable) {
                Log.w(ETIQUETA, "No se pudo enviar el correo de la cita $idCita", t)
                alTerminar(false, "No se pudo conectar con el servidor de correos")
            }
        })
    }
}

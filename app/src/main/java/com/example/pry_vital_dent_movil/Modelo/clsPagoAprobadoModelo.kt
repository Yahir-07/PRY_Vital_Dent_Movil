package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsPagoAprobadoModelo(private val contexto: Context) {

    // GET estado_pago.php: datos del comprobante guardado en la tabla pagos
    fun ObtenerDetallesComprobante(referencia: String, alTerminar: (Boolean, String, DataComprobante?) -> Unit) {
        val llamada = RetrofitClient.api.ConsultarEstadoPago(clsSesion.ObtenerToken(contexto), referencia)
        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            val comprobante = if (exito && datos?.estado == "aprobado") datos.comprobante else null
            alTerminar(comprobante != null, mensaje, comprobante)
        }
    }
}

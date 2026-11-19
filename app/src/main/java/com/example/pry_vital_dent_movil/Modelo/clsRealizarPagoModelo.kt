package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsRealizarPagoModelo(private val contexto: Context) {

    private val preferencias = contexto.getSharedPreferences("VitalDentPagos", Context.MODE_PRIVATE)

    // GET opciones_pago.php: tratamientos con saldo pendiente
    fun ObtenerOpcionesPago(alTerminar: (Boolean, String, List<DataOpcionPago>, Boolean) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerOpcionesPago(clsSesion.ObtenerToken(contexto))
        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito, mensaje, datos?.opciones ?: emptyList(), datos?.mercadoPagoActivo ?: false)
        }
    }

    // POST crear_pago.php: crea el pago en Mercado Pago y regresa la dirección donde se paga
    fun CrearPago(idTratamiento: String, monto: Double, claveIntento: String, alTerminar: (Boolean, String, DatosPagoCreado?) -> Unit) {
        val llamada = RetrofitClient.api.CrearPago(
            clsSesion.ObtenerToken(contexto),
            SolicitudPago(idTratamiento, monto, claveIntento)
        )
        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito && datos != null, mensaje, datos)
        }
    }

    // GET estado_pago.php: cómo quedó el pago (si se aprobó, el servidor lo registra una sola vez)
    fun ConsultarEstadoPago(referencia: String, alTerminar: (Boolean, String, DatosEstadoPago?) -> Unit) {
        val llamada = RetrofitClient.api.ConsultarEstadoPago(clsSesion.ObtenerToken(contexto), referencia)
        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito && datos != null, mensaje, datos)
        }
    }

    // Pago que se abrió en Mercado Pago y todavía no se confirma (sobrevive si Android cierra la app)
    fun GuardarPagoEnCurso(referencia: String) {
        preferencias.edit().putString("PAGO_EN_CURSO", referencia).apply()
    }

    fun ObtenerPagoEnCurso(): String = preferencias.getString("PAGO_EN_CURSO", "") ?: ""

    fun BorrarPagoEnCurso() {
        preferencias.edit().remove("PAGO_EN_CURSO").apply()
    }

    // Preferencia del perfil "Huella para pagos"
    fun HuellaPagosActiva(): Boolean = clsSesion.HuellaPagosActiva(contexto)
}

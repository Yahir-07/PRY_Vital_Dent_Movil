package com.example.pry_vital_dent_movil.Modelo

// ---------- Pagos con Mercado Pago (Checkout Pro) ----------

// Lo que la app envía a crear_pago.php. claveIntento evita cobros duplicados si se toca "Pagar" dos veces.
data class SolicitudPago(val idTratamiento: String, val monto: Double, val claveIntento: String)

// Tratamiento con saldo por pagar (opciones_pago.php)
data class DataOpcionPago(
    val idTratamiento: String,
    val concepto: String,
    val saldo: Double,
    val saldoTexto: String
)

data class DatosOpcionesPago(
    val opciones: List<DataOpcionPago>?,
    val mercadoPagoActivo: Boolean
)

// Respuesta de crear_pago.php: urlPago es la página segura de Mercado Pago
data class DatosPagoCreado(
    val referencia: String,
    val urlPago: String,
    val concepto: String,
    val monto: String
)

// Datos del comprobante (pantalla Pago aprobado)
data class DataComprobante(
    val folio: String,
    val concepto: String,
    val monto: String,
    val fecha: String,
    val medio: String,
    val autorizado: String
)

// estado_pago.php: estado = "aprobado" | "pendiente" | "rechazado" | "sin_pago"
data class DatosEstadoPago(
    val estado: String,
    val comprobante: DataComprobante?
)

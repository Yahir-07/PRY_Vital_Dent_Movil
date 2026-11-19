package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.DataOpcionPago
import com.example.pry_vital_dent_movil.Modelo.clsRealizarPagoModelo
import com.example.pry_vital_dent_movil.Vista.clsRealizarPagoVista
import java.util.Locale
import java.util.UUID

class clsRealizarPagoPresentador(private val vista: clsRealizarPagoVista) {

    private val modelo = clsRealizarPagoModelo(vista)

    private var opciones: List<DataOpcionPago> = emptyList()
    private var seleccionada: DataOpcionPago? = null
    private var montoAPagar: Double = 0.0
    private var pagosActivos = false
    private var procesando = false

    // Clave del intento de pago (RF-45): se reutiliza mientras no cambien el tratamiento ni el monto,
    // así dos toques en "Pagar" no generan dos cobros.
    private var claveIntento = ""
    private var datosDeLaClave = ""

    companion object {
        const val MONTO_MINIMO = 10.0
    }

    fun CargarDatosPago() {
        modelo.ObtenerOpcionesPago { exito, mensaje, lista, mercadoPagoActivo ->
            opciones = lista
            pagosActivos = mercadoPagoActivo
            if (!exito) {
                vista.MostrarMensaje(mensaje)
                return@ObtenerOpcionesPago
            }
            if (lista.isEmpty()) {
                vista.MostrarSinSaldo()
                return@ObtenerOpcionesPago
            }
            vista.MostrarOpciones(lista.map { "${it.concepto} · ${it.saldoTexto}" })
            SeleccionarTratamiento(0)
            if (!mercadoPagoActivo) {
                vista.MostrarMensaje("Los pagos en línea todavía no están disponibles")
            }
        }
    }

    fun SeleccionarTratamiento(posicion: Int) {
        val opcion = opciones.getOrNull(posicion) ?: return
        seleccionada = opcion
        vista.MostrarDetalles(opcion.concepto, opcion.saldoTexto)
        vista.MostrarMontoSugerido(String.format(Locale.US, "%.2f", opcion.saldo), opcion.saldoTexto)
        CambiarMonto(opcion.saldo.toString())
    }

    fun CambiarMonto(montoTexto: String) {
        montoAPagar = montoTexto.replace(",", "").toDoubleOrNull() ?: 0.0
        vista.MostrarTotalBoton(if (montoAPagar > 0) String.format(Locale.US, "$%,.2f", montoAPagar) else "")
    }

    // PAG-02: validar selección, monto y medio antes de pagar
    fun ClickPagar(montoTexto: String) {
        if (procesando) return
        val opcion = seleccionada
        if (opcion == null) {
            vista.MostrarMensaje("Selecciona el tratamiento que vas a pagar")
            return
        }
        CambiarMonto(montoTexto)
        if (montoAPagar < MONTO_MINIMO) {
            vista.MostrarErrorMonto(String.format(Locale.US, "El monto mínimo es $%,.2f", MONTO_MINIMO))
            return
        }
        if (montoAPagar > opcion.saldo) {
            vista.MostrarErrorMonto("No puede ser mayor al saldo pendiente (${opcion.saldoTexto})")
            return
        }
        if (!pagosActivos) {
            vista.MostrarMensaje("Los pagos en línea todavía no están disponibles")
            return
        }
        vista.MostrarErrorMonto(null)

        val datos = opcion.idTratamiento + "|" + String.format(Locale.US, "%.2f", montoAPagar)
        if (claveIntento.isEmpty() || datos != datosDeLaClave) {
            claveIntento = UUID.randomUUID().toString()
            datosDeLaClave = datos
        }

        // PAG-03 (Gustavo): si el paciente activó "Huella para pagos", primero se valida la huella
        if (modelo.HuellaPagosActiva()) {
            vista.IrAValidacionBiometrica(String.format(Locale.US, "%s - $%,.2f", opcion.concepto, montoAPagar))
        } else {
            IniciarPagoMercadoPago()
        }
    }

    fun ResultadoValidacionBiometrica(aprobada: Boolean) {
        if (aprobada) {
            IniciarPagoMercadoPago()
        } else {
            vista.MostrarMensaje("Pago cancelado: no se validó la huella")
        }
    }

    private fun IniciarPagoMercadoPago() {
        val opcion = seleccionada ?: return
        procesando = true
        vista.MostrarProcesando(true)

        modelo.CrearPago(opcion.idTratamiento, montoAPagar, claveIntento) { exito, mensaje, datos ->
            procesando = false
            vista.MostrarProcesando(false)
            if (exito && datos != null) {
                modelo.GuardarPagoEnCurso(datos.referencia)
                vista.AbrirMercadoPago(datos.urlPago)
            } else {
                vista.MostrarMensaje(mensaje)
                CargarDatosPago() // el saldo pudo cambiar (por ejemplo, el pago ya se había registrado)
            }
        }
    }

    // Al volver de Mercado Pago (onResume o enlace vitaldent://pago?referencia=...)
    fun AlVolverALaApp(referenciaDelEnlace: String?) {
        val referencia = referenciaDelEnlace?.takeIf { it.isNotEmpty() } ?: modelo.ObtenerPagoEnCurso()
        if (referencia.isEmpty() || procesando) return

        procesando = true
        modelo.ConsultarEstadoPago(referencia) { exito, mensaje, datos ->
            procesando = false
            if (!exito || datos == null) {
                vista.MostrarMensaje(mensaje)
                return@ConsultarEstadoPago
            }
            when (datos.estado) {
                "aprobado" -> {
                    modelo.BorrarPagoEnCurso()
                    claveIntento = ""
                    vista.IrAPagoAprobado(referencia)
                }
                "rechazado" -> {
                    modelo.BorrarPagoEnCurso()
                    claveIntento = "" // el siguiente intento es un pago nuevo
                    vista.MostrarMensaje("Pago rechazado: $mensaje No se hizo ningún cargo.")
                }
                "pendiente" -> vista.MostrarMensaje(mensaje)
                else -> vista.MostrarMensaje("No se completó el pago en Mercado Pago")
            }
        }
    }
}

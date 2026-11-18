package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.DataPago
import com.example.pry_vital_dent_movil.Modelo.clsPagosModelo
import com.example.pry_vital_dent_movil.Vista.clsPagosVista

class clsPagosPresentador(private val vista: clsPagosVista) {

    private val modelo = clsPagosModelo(vista)

    fun CargarDatosIniciales() {
        modelo.ObtenerSaldoPendiente { monto, concepto ->
            vista.MostrarSaldo(monto, concepto)
        }
        CambiarPestana(0) // Por defecto muestra Realizados
    }

    fun CambiarPestana(posicionTab: Int) {
        modelo.ObtenerListaPagos(posicionTab) { exito, mensaje, listaPagos ->
            vista.MostrarListaPagos(listaPagos)
            if (!exito) {
                vista.MostrarMensaje(mensaje)
            } else if (listaPagos.isEmpty()) {
                vista.MostrarMensaje(if (posicionTab == 0) "Aún no tienes pagos registrados" else "No tienes pagos pendientes")
            }
        }
    }

    fun SeleccionarPago(pago: DataPago) {
        vista.MostrarMensaje("Pago seleccionado: ${pago.concepto} (${pago.estado})")
    }

    fun ClickPagarAhora() {
        vista.IrARealizarPago()
    }
}

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
                vista.MostrarAvisoError(mensaje)
            } else if (listaPagos.isEmpty()) {
                vista.MostrarAviso(if (posicionTab == 0) "Aún no tienes pagos registrados" else "No tienes pagos pendientes")
            } else {
                vista.OcultarAviso()
            }
        }
    }

    fun SeleccionarPago(pago: DataPago) {
        // Por ahora no hace nada, aquí se puede abrir el detalle después
    }

    fun ClickPagarAhora() {
        vista.IrARealizarPago()
    }
}

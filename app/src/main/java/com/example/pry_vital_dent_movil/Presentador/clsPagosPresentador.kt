package com.example.pry_vital_dent_movil.Presentador

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
        modelo.ObtenerListaPagos(posicionTab) { listaPagos ->
            vista.MostrarListaPagos(listaPagos)
        }
    }

    fun ClickPagarAhora() {
        vista.IrARealizarPago()
    }
}
package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsPagosModelo(private val contexto: Context) {

    // GET pagos.php: saldo pendiente (costo de tratamientos menos pagos aprobados)
    fun ObtenerSaldoPendiente(alTerminar: (String, String) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerPagos(clsSesion.ObtenerToken(contexto), "pendientes")

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            if (exito && datos != null) {
                alTerminar(datos.saldoMonto, datos.saldoConcepto)
            } else {
                alTerminar("--", mensaje)
            }
        }
    }

    // Pestaña 0 = Realizados, Pestaña 1 = Pendientes
    fun ObtenerListaPagos(pestana: Int, alTerminar: (Boolean, String, List<DataPago>) -> Unit) {
        val tipo = if (pestana == 0) "realizados" else "pendientes"
        val llamada = RetrofitClient.api.ObtenerPagos(clsSesion.ObtenerToken(contexto), tipo)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito, mensaje, datos?.lista ?: emptyList())
        }
    }
}

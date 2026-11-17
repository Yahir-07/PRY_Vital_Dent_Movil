package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsTipoBracketsModelo
import com.example.pry_vital_dent_movil.Vista.clsTipoBracketsVista

class clsTipoBracketsPresentador(private val vista: clsTipoBracketsVista) {

    private val modelo = clsTipoBracketsModelo(vista)

    fun ClickVerCaracteristicas(estaOculto: Boolean): Boolean {
        // Alterna el estado de visibilidad de las características
        return !estaOculto
    }

    fun ClickSolicitarValoracion(tipoBracket: String) {
        modelo.RegistrarSolicitudValoracion(tipoBracket) { exito, mensaje ->
            if (exito) {
                vista.MostrarMensaje(mensaje)
                vista.IrASolicitudExitosa()
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun FiltrarBrackets(posicionTab: Int) {
        when (posicionTab) {
            0 -> vista.MostrarFiltroTodos()
            1 -> vista.MostrarFiltroEsteticos()
            2 -> vista.MostrarFiltroClasicos()
        }
    }
}
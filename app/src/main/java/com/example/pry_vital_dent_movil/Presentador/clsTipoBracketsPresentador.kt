package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsTipoBracketsModelo
import com.example.pry_vital_dent_movil.Vista.clsTipoBracketsVista
import com.example.pry_vital_dent_movil.Modelo.clsImagenesModelo

class clsTipoBracketsPresentador(private val vista: clsTipoBracketsVista) {

    private val modelo = clsTipoBracketsModelo(vista)

    fun ClickVerCaracteristicas(estaOculto: Boolean): Boolean {
        return !estaOculto
    }

    fun ClickSolicitarValoracion(tipoBracket: String) {
        val motivo = modelo.ArmarMotivoValoracion(tipoBracket)
        vista.IrAAgendarValoracion(motivo) // Agendar cita se abre con el motivo ya escrito
    }

    fun FiltrarBrackets(posicionTab: Int) {
        when (posicionTab) {
            0 -> vista.MostrarFiltroTodos()
            1 -> vista.MostrarFiltroEsteticos()
            2 -> vista.MostrarFiltroClasicos()
        }
    }
    fun CargarImagenes() {
        modelo.ObtenerImagenes { imagenes ->
            vista.MostrarImagenes(
                imagenes[clsImagenesModelo.BRACKETS_METALICOS],
                imagenes[clsImagenesModelo.BRACKETS_CERAMICOS],
                imagenes[clsImagenesModelo.BRACKETS_ZAFIRO],
                imagenes[clsImagenesModelo.BRACKETS_AUTOLIGABLES]
            )
        }
    }
}
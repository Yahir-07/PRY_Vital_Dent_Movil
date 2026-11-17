package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsSesionVencidaModelo
import com.example.pry_vital_dent_movil.Vista.clsSesionVencidaVista

class clsSesionVencidaPresentador(private val vista: clsSesionVencidaVista) {

    private val modelo = clsSesionVencidaModelo(vista)

    fun ClickIniciarSesion() {
        vista.IrALogin()
    }

    fun ClickUsarHuella() {
        modelo.ValidarReingresoHuella { exito, mensaje ->
            vista.MostrarMensaje(mensaje)
            if (exito) {
                vista.IrAInicio()
            }
        }
    }
}
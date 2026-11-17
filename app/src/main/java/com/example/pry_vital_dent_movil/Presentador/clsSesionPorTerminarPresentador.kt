package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsSesionPorTerminarModelo
import com.example.pry_vital_dent_movil.Vista.clsSesionPorTerminarVista

class clsSesionPorTerminarPresentador(private val vista: clsSesionPorTerminarVista) {

    private val modelo = clsSesionPorTerminarModelo(vista)

    fun ClickSeguirConectado() {
        modelo.RenovarSesion { mensaje ->
            vista.MostrarMensaje(mensaje)
            vista.RegresarAlFlujo()
        }
    }

    fun ClickCerrarSesion() {
        modelo.DestruirSesion {
            vista.CerrarSesionYIrALogin()
        }
    }
}
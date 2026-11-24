package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsBienvenidoModelo
import com.example.pry_vital_dent_movil.Modelo.clsImagenesModelo
import com.example.pry_vital_dent_movil.Vista.MainActivity

class clsBienvenidoPresentador(private val actividad: MainActivity) {

    private val modelo = clsBienvenidoModelo(actividad)

    fun VerificarSesion() {
        val token = modelo.ObtenerTokenGuardado()
        if (token.isNotEmpty()) {
            actividad.IrAlInicio()
        }
    }

    fun ClickIniciarSesion() {
        actividad.IrALogin()
    }

    fun ClickCrearCuenta() {
        actividad.IrARegistro()
    }
    fun CargarImagenes() {
        modelo.ObtenerImagenes { imagenes ->
            actividad.MostrarImagenes(imagenes[clsImagenesModelo.LOGO], imagenes[clsImagenesModelo.FOTO_BIENVENIDA])
        }
    }
}
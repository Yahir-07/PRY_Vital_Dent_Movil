package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsNuestrosDoctoresModelo
import com.example.pry_vital_dent_movil.Vista.clsNuestrosDoctoresVista

class clsNuestrosDoctoresPresentador(private val vista: clsNuestrosDoctoresVista) {

    private val modelo = clsNuestrosDoctoresModelo(vista)

    fun CargarDoctores() {
        modelo.ObtenerListaDoctores { exito, mensaje, lista ->
            vista.MostrarListaDoctores(lista)
            if (!exito) {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun SeleccionarDoctor(idDoctor: String) {
        vista.IrAPerfilDoctor(idDoctor) // Pantalla 21: Perfil del doctor
    }
}
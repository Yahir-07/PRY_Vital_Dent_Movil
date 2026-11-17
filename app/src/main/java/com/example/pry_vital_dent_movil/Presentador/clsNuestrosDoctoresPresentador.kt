package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsNuestrosDoctoresModelo
import com.example.pry_vital_dent_movil.Vista.clsNuestrosDoctoresVista

class clsNuestrosDoctoresPresentador(private val vista: clsNuestrosDoctoresVista) {

    private val modelo = clsNuestrosDoctoresModelo(vista)

    fun CargarDoctores() {
        modelo.ObtenerListaDoctores { lista ->
            vista.MostrarListaDoctores(lista)
        }
    }

    fun SeleccionarDoctor(doctorInfo: String) {
        vista.IrAPerfilDoctor(doctorInfo) // Pantalla 21: Perfil del doctor
    }
}
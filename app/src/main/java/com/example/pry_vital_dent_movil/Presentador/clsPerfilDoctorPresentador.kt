package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsPerfilDoctorModelo
import com.example.pry_vital_dent_movil.Vista.clsPerfilDoctorVista

class clsPerfilDoctorPresentador(private val vista: clsPerfilDoctorVista) {

    private val modelo = clsPerfilDoctorModelo(vista)

    fun CargarDatosDoctor() {
        modelo.ObtenerDetalleDoctor { iniciales, nombre, especialidad, clinica, cedula, descripcion ->
            vista.MostrarDetalles(iniciales, nombre, especialidad, clinica, cedula, descripcion)
        }

        modelo.ObtenerOtrosDoctores { listaOtros ->
            vista.MostrarOtrosDoctores(listaOtros)
        }
    }

    fun ClickAgendarCita() {
        vista.IrAAgendarCita()
    }

    fun ClickLlamarConsultorio() {
        vista.LlamarConsultorio()
    }
}
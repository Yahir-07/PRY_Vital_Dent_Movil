package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.DataDoctorCompacto
import com.example.pry_vital_dent_movil.Modelo.clsPerfilDoctorModelo
import com.example.pry_vital_dent_movil.Vista.clsPerfilDoctorVista

class clsPerfilDoctorPresentador(private val vista: clsPerfilDoctorVista) {

    private val modelo = clsPerfilDoctorModelo(vista)
    private var idDoctorActual: String = ""

    // idDoctor vacío = primer doctor de la lista
    fun CargarDatosDoctor(idDoctor: String) {
        modelo.ObtenerPerfilDoctor(idDoctor) { exito, mensaje, datos ->
            if (exito && datos != null) {
                idDoctorActual = datos.idDoctor
                vista.MostrarDetalles(datos.iniciales, datos.nombre, datos.especialidad, datos.clinica, datos.cedula, datos.descripcion)
                vista.MostrarOtrosDoctores(datos.otros ?: emptyList())
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    fun SeleccionarOtroDoctor(doctor: DataDoctorCompacto) {
        vista.MostrarMensaje("Abriendo perfil de: ${doctor.nombre}")
        CargarDatosDoctor(doctor.idDoctor)
    }

    fun ClickAgendarCita() {
        vista.IrAAgendarCita(idDoctorActual)
    }

    fun ClickLlamarConsultorio() {
        vista.LlamarConsultorio()
    }
}

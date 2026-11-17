package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsPerfilDoctorModelo(private val contexto: Context) {

    fun ObtenerDetalleDoctor(alTerminar: (String, String, String, String, String, String) -> Unit) {
        val iniciales = "AR"
        val nombre = "Dr. Andrés Ruiz"
        val especialidad = "Ortodoncia"
        val clinica = "Clínica Vital Dent, Huejutla"
        val cedula = "Cédula profesional 15300001"
        val descripcion = "Especialista en corrección de la alineación dental con brackets y alineadores."

        alTerminar(iniciales, nombre, especialidad, clinica, cedula, descripcion)
    }

    fun ObtenerOtrosDoctores(alTerminar: (List<String>) -> Unit) {
        val otros = listOf(
            "Dr. Luis Méndez - Implantología",
            "Dra. Sofía Torres - Estética dental"
        )
        alTerminar(otros)
    }
}
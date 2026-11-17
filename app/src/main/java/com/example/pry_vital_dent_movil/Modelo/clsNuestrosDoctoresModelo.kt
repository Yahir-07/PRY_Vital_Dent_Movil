package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsNuestrosDoctoresModelo(private val contexto: Context) {

    fun ObtenerListaDoctores(alTerminar: (List<DataDoctor>) -> Unit) {
        // Aquí posteriormente harás tu llamada a tu API / Base de datos (PHP/Retrofit)
        // Por ahora dejamos una lista de prueba para verificar que pinte las tarjetas:
        val listaDoctores = listOf(
            DataDoctor("AR", "Dr. Andrés Ruiz", "Ortodoncia", "Especialista en corrección de la alineación dental con brackets y alineadores.", "10000001"),
            DataDoctor("LM", "Dr. Luis Méndez", "Implantología", "Rehabilita sonrisas y devuelve la función al masticar con implantes.", "10000002"),
            DataDoctor("ST", "Dra. Sofía Torres", "Estética dental", "Blanqueamientos y diseño de sonrisa con resultados naturales.", "10000003")
        )

        alTerminar(listaDoctores)
    }
}
package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsAgendarCitaModelo(private val contexto: Context) {

    // GET horarios.php?fecha=dd/MM/yyyy&idDoctor=: horas del día y si están libres
    fun ObtenerHorasDisponibles(fecha: String, idDoctor: String, alTerminar: (Boolean, String, List<DataHora>) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerHorarios(clsSesion.ObtenerToken(contexto), fecha, idDoctor)

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(exito, mensaje, datos ?: emptyList())
        }
    }

    // POST agendar_cita.php: guarda la cita en la tabla citas
    // (si idDoctor está vacío, el servidor asigna un doctor libre)
    fun RegistrarCita(fecha: String, hora: String, motivo: String, idDoctor: String, alTerminar: (Boolean, String) -> Unit) {
        val llamada = RetrofitClient.api.AgendarCita(
            clsSesion.ObtenerToken(contexto),
            SolicitudCita(fecha, hora, motivo, idDoctor)
        )

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            alTerminar(exito, mensaje)
        }
    }
}

package com.example.pry_vital_dent_movil.Modelo

import android.content.Context

class clsPerfilPacienteModelo(private val contexto: Context) {

    // GET perfil.php: teléfono, correo y domicilio del paciente
    fun ObtenerDatosPerfil(alTerminar: (datos: DatosPerfil?, mensaje: String, huellaActiva: Boolean) -> Unit) {
        val llamada = RetrofitClient.api.ObtenerPerfil(clsSesion.ObtenerToken(contexto))

        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, datos ->
            alTerminar(if (exito) datos else null, mensaje, clsSesion.HuellaPagosActiva(contexto))
        }
    }

    // La preferencia de huella para pagos se guarda en el teléfono (SharedPreferences)
    fun ActualizarEstadoHuella(activada: Boolean, alTerminar: (Boolean) -> Unit) {
        clsSesion.GuardarHuellaPagos(contexto, activada)
        alTerminar(true)
    }

    fun CerrarSesion() {
        clsSesion.Cerrar(contexto)
    }
}

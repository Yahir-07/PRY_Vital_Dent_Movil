package com.example.pry_vital_dent_movil.Modelo

// Forma en la que responden todas las APIs PHP de APIS_proyecto:
// { "exito": true, "mensaje": "...", "datos": { ... } }
data class RespuestaApi<T>(
    val exito: Boolean,
    val mensaje: String?,
    val datos: T?
)

// ---------- Lo que la app envía ----------

data class SolicitudLogin(val correo: String, val contrasena: String)

data class SolicitudRegistro(val nombre: String, val correo: String, val contrasena: String)

data class SolicitudCambioContrasena(val actual: String, val nueva: String)

data class SolicitudCita(val fecha: String, val hora: String, val motivo: String)

// ---------- Lo que la app recibe ----------

data class DatosLogin(
    val token: String,
    val nombre: String,
    val idPaciente: Long,
    val cuestionarioCompleto: Boolean
)

data class DatosRegistro(val idUsuario: Long)

data class DatosInicio(
    val nombre: String,
    val citaTitulo: String,
    val citaDetalle: String,
    val tratamientoNombre: String,
    val pagosTexto: String
)

data class DatosPerfil(
    val nombre: String,
    val telefono: String,
    val correo: String,
    val domicilio: String
)

// Casilla de horario para agendar cita: valor = "10:00", texto = "10:00 a. m."
data class DataHora(
    val valor: String,
    val texto: String,
    val disponible: Boolean
)

data class DatosPagos(
    val saldoMonto: String,
    val saldoConcepto: String,
    val lista: List<String>?
)

data class DatosHistorial(
    val alergias: List<String>?,
    val historial: List<String>?
)

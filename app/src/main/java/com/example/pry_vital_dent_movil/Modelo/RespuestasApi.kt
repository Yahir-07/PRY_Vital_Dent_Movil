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

// Foto de perfil en JPG codificada en base64 (foto_perfil.php)
data class SolicitudFotoPerfil(val foto: String)

// idDoctor vacío = el servidor asigna un doctor libre
data class SolicitudCita(val fecha: String, val hora: String, val motivo: String, val idDoctor: String = "")

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

data class DatosPagos(
    val saldoMonto: String,
    val saldoConcepto: String,
    val lista: List<DataPago>?
)

data class DatosHistorial(
    val alergias: List<String>?,
    val historial: List<DataHistorial>?
)

data class DatosPerfilDoctor(
    val idDoctor: String,
    val iniciales: String,
    val nombre: String,
    val especialidad: String,
    val clinica: String,
    val cedula: String,
    val descripcion: String,
    val otros: List<DataDoctorCompacto>?
)

data class DatosAvanceTratamiento(
    val idTratamiento: String,
    val nombre: String,
    val porcentaje: String,
    val sesiones: String,
    val finEstimado: String,
    val estado: String,
    val avances: List<DataAvance>?
)

// foto = JPG en base64, o null si el paciente todavía no tiene foto
data class DatosFotoPerfil(val foto: String?)

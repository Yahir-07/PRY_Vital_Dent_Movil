package com.example.pry_vital_dent_movil.InterfaceAPI

import com.example.pry_vital_dent_movil.Modelo.DataCita
import com.example.pry_vital_dent_movil.Modelo.DataDoctor
import com.example.pry_vital_dent_movil.Modelo.DataHora
import com.example.pry_vital_dent_movil.Modelo.DatosHistorial
import com.example.pry_vital_dent_movil.Modelo.DatosInicio
import com.example.pry_vital_dent_movil.Modelo.DatosLogin
import com.example.pry_vital_dent_movil.Modelo.DatosPagos
import com.example.pry_vital_dent_movil.Modelo.DatosPerfil
import com.example.pry_vital_dent_movil.Modelo.DatosRegistro
import com.example.pry_vital_dent_movil.Modelo.RespuestaApi
import com.example.pry_vital_dent_movil.Modelo.RetrofitClient
import com.example.pry_vital_dent_movil.Modelo.SolicitudCambioContrasena
import com.example.pry_vital_dent_movil.Modelo.SolicitudCita
import com.example.pry_vital_dent_movil.Modelo.SolicitudLogin
import com.example.pry_vital_dent_movil.Modelo.SolicitudRegistro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

// Cada función corresponde a un archivo PHP de C:\xampp\htdocs\APIS_proyecto
interface ApiService {

    // ---------- Cuenta ----------

    @POST("login.php")
    fun IniciarSesion(@Body datos: SolicitudLogin): Call<RespuestaApi<DatosLogin>>

    @POST("registro.php")
    fun Registrar(@Body datos: SolicitudRegistro): Call<RespuestaApi<DatosRegistro>>

    @GET("perfil.php")
    fun ObtenerPerfil(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String
    ): Call<RespuestaApi<DatosPerfil>>

    @POST("cambiar_contrasena.php")
    fun CambiarContrasena(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String,
        @Body datos: SolicitudCambioContrasena
    ): Call<RespuestaApi<Any>>

    // ---------- Panel principal ----------

    @GET("inicio.php")
    fun ObtenerInicio(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String
    ): Call<RespuestaApi<DatosInicio>>

    // ---------- Doctores (público) ----------

    @GET("doctores.php")
    fun ObtenerDoctores(): Call<RespuestaApi<List<DataDoctor>>>

    // ---------- Citas ----------

    // tipo = "proximas" o "anteriores"
    @GET("citas.php")
    fun ObtenerCitas(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String,
        @Query("tipo") tipo: String
    ): Call<RespuestaApi<List<DataCita>>>

    // fecha = "dd/MM/yyyy"
    @GET("horarios.php")
    fun ObtenerHorarios(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String,
        @Query("fecha") fecha: String
    ): Call<RespuestaApi<List<DataHora>>>

    @POST("agendar_cita.php")
    fun AgendarCita(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String,
        @Body datos: SolicitudCita
    ): Call<RespuestaApi<DataCita>>

    // ---------- Tratamientos, pagos e historial ----------

    // estado = "en_curso" o "finalizado"
    @GET("tratamientos.php")
    fun ObtenerTratamientos(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String,
        @Query("estado") estado: String
    ): Call<RespuestaApi<List<String>>>

    // tipo = "realizados" o "pendientes"
    @GET("pagos.php")
    fun ObtenerPagos(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String,
        @Query("tipo") tipo: String
    ): Call<RespuestaApi<DatosPagos>>

    // filtro = "Todo", "Consultas", "Recetas" o "Tratamientos"
    @GET("historial.php")
    fun ObtenerHistorial(
        @Header(RetrofitClient.ENCABEZADO_TOKEN) token: String,
        @Query("filtro") filtro: String
    ): Call<RespuestaApi<DatosHistorial>>
}

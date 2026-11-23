package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pry_vital_dent_movil.Presentador.clsInicioPresentador
import com.example.pry_vital_dent_movil.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.pry_vital_dent_movil.Notificaciones.MyFirebaseMessagingService

class clsInicioVista : AppCompatActivity() {

    private lateinit var presentador: clsInicioPresentador
    private lateinit var tbInicio: MaterialToolbar
    private lateinit var cvProximaCita: MaterialCardView
    private lateinit var tvProximaCitaTitulo: TextView
    private lateinit var tvProximaCitaDetalle: TextView
    private lateinit var cvTratamientoActivo: MaterialCardView
    private lateinit var tvTratamientoActivoNombre: TextView
    private lateinit var cvAgendarCita: MaterialCardView
    private lateinit var cvTratamientos: MaterialCardView
    private lateinit var cvHistorialMedico: MaterialCardView
    private lateinit var cvPagos: MaterialCardView
    private lateinit var tvPagosPendientes: TextView
    private lateinit var cvMultimedia: MaterialCardView
    private val permisoNotificaciones =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_principal)

        val contenedor = findViewById<android.view.ViewGroup>(R.id.contenedorFragmentos)
        val vistaTarjetas = layoutInflater.inflate(R.layout.fragment_inicio, contenedor, true)

        tbInicio = vistaTarjetas.findViewById(R.id.tbInicio)
        cvProximaCita = vistaTarjetas.findViewById(R.id.cvProximaCita)
        tvProximaCitaTitulo = vistaTarjetas.findViewById(R.id.tvProximaCitaTitulo)
        tvProximaCitaDetalle = vistaTarjetas.findViewById(R.id.tvProximaCitaDetalle)
        cvTratamientoActivo = vistaTarjetas.findViewById(R.id.cvTratamientoActivo)
        tvTratamientoActivoNombre = vistaTarjetas.findViewById(R.id.tvTratamientoActivoNombre)
        cvAgendarCita = vistaTarjetas.findViewById(R.id.cvAgendarCita)
        cvTratamientos = vistaTarjetas.findViewById(R.id.cvTratamientos)
        cvHistorialMedico = vistaTarjetas.findViewById(R.id.cvHistorialMedico)
        cvPagos = vistaTarjetas.findViewById(R.id.cvPagos)
        tvPagosPendientes = vistaTarjetas.findViewById(R.id.tvPagosPendientes)
        cvMultimedia = vistaTarjetas.findViewById(R.id.cvMultimedia)

        presentador = clsInicioPresentador(this)
        presentador.InicializarPanel()

        cvProximaCita.setOnClickListener { presentador.ClickProximaCita() }
        cvTratamientoActivo.setOnClickListener { presentador.ClickTratamientoActivo() }
        cvAgendarCita.setOnClickListener { presentador.ClickAgendarCita() }
        cvTratamientos.setOnClickListener { presentador.ClickTratamientos() }
        cvHistorialMedico.setOnClickListener { presentador.ClickHistorialMedico() }
        cvPagos.setOnClickListener { presentador.ClickPagos() }
        cvMultimedia.setOnClickListener { presentador.ClickMultimedia() }

        configurarBarraInferior(0)

        MyFirebaseMessagingService.crearCanal(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permisoNotificaciones.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun MostrarDatosUsuario(nombre: String) {
        tbInicio.title = "Hola, $nombre"
        tbInicio.subtitle = "Paciente"
    }

    fun MostrarDatosProximaCita(titulo: String, detalle: String) {
        tvProximaCitaTitulo.text = titulo
        tvProximaCitaDetalle.text = detalle
    }

    fun MostrarDatosTratamiento(nombreTratamiento: String) {
        tvTratamientoActivoNombre.text = nombreTratamiento
    }

    fun MostrarPagosPendientes(textoPagos: String) {
        tvPagosPendientes.text = textoPagos
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    fun IrADetalleCita() {
        startActivity(Intent(this, clsMisCitasVista::class.java))
    }

    fun IrAMisCitas() {
        startActivity(Intent(this, clsMisCitasVista::class.java))
    }

    fun IrADetalleTratamiento() {
        startActivity(Intent(this, clsAvanceTratamientoVista::class.java))
    }

    fun IrAAgendarCita() {
        startActivity(Intent(this, clsAgendarCitaVista::class.java))
    }

    fun IrAListaTratamientos() {
        startActivity(Intent(this, clsMisTratamientosVista::class.java))
    }

    fun IrAHistorialMedico() {
        startActivity(Intent(this, clsHistorialMedicoVista::class.java))
    }

    fun IrAPagos() {
        startActivity(Intent(this, clsPagosVista::class.java))
    }

    fun IrAMultimedia() {
        startActivity(Intent(this, clsMultimediaVista::class.java))
    }

    fun IrAPerfil() { // <-- Agregado para que el presentador abra el perfil correctamente desde la barra
        startActivity(Intent(this, clsPerfilPacienteVista::class.java))
    }
}
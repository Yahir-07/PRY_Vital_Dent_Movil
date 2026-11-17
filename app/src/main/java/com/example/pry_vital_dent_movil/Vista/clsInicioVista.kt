package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pry_vital_dent_movil.Presentador.clsInicioPresentador
import com.example.pry_vital_dent_movil.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inflamos el layout principal que contiene la barra inferior
        setContentView(R.layout.activity_principal)

        // 2. Insertamos el diseño de las tarjetas (fragment_inicio) dentro del contenedor central
        val contenedor = findViewById<android.view.ViewGroup>(R.id.contenedorFragmentos)
        val vistaTarjetas = layoutInflater.inflate(R.layout.fragment_inicio, contenedor, true)

        // 3. Inicializamos los componentes buscando dentro de la vista de las tarjetas
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

        presentador = clsInicioPresentador(this)
        presentador.InicializarPanel()

        // 4. Configuración de clics en las tarjetas
        cvProximaCita.setOnClickListener { presentador.ClickProximaCita() }
        cvTratamientoActivo.setOnClickListener { presentador.ClickTratamientoActivo() }
        cvAgendarCita.setOnClickListener { presentador.ClickAgendarCita() }
        cvTratamientos.setOnClickListener { presentador.ClickTratamientos() }
        cvHistorialMedico.setOnClickListener { presentador.ClickHistorialMedico() }
        cvPagos.setOnClickListener { presentador.ClickPagos() }

        // 5. Activamos la barra de navegación inferior (Inicio es la posición 0)
        configurarBarraInferior(0)
    }

    // Métodos para actualizar la interfaz visualmente
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

    // ==========================================
    // MÉTODOS DE NAVEGACIÓN (Tarjetas y Barra)
    // ==========================================

    fun IrADetalleCita() {
        startActivity(Intent(this, clsMisCitasVista::class.java))
    }

    fun IrAMisCitas() { // <-- Agregado para que el presentador no marque error al usar la barra inferior
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

    fun IrAPerfil() { // <-- Agregado para que el presentador abra el perfil correctamente desde la barra
        startActivity(Intent(this, clsPerfilPacienteVista::class.java))
    }
}
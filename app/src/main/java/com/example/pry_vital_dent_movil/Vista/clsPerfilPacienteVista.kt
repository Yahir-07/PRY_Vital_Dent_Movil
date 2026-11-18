package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.materialswitch.MaterialSwitch
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsPerfilPacientePresentador
import com.google.android.material.appbar.MaterialToolbar

class clsPerfilPacienteVista : AppCompatActivity() {

    private lateinit var presentador: clsPerfilPacientePresentador
    private lateinit var tbPerfil: MaterialToolbar
    private lateinit var tvDatosContacto: TextView
    private lateinit var btnEditarContacto: MaterialButton
    private lateinit var cvDatosContacto: MaterialCardView
    private lateinit var ivTelefono: ImageView
    private lateinit var tvTelefono: TextView
    private lateinit var tvTelefonoEtiqueta: TextView
    private lateinit var ivCorreo: ImageView
    private lateinit var tvCorreo: TextView
    private lateinit var tvCorreoEtiqueta: TextView
    private lateinit var ivDomicilio: ImageView
    private lateinit var tvDomicilio: TextView
    private lateinit var tvDomicilioEtiqueta: TextView
    private lateinit var tvConsultorio: TextView
    private lateinit var cvConsultorio: MaterialCardView
    private lateinit var tvNuestrosDoctores: TextView
    private lateinit var tvContactoHorario: TextView
    private lateinit var tvCuenta: TextView
    private lateinit var cvCuenta: MaterialCardView
    private lateinit var tvCambiarContrasena: TextView
    private lateinit var ivHuellaPagos: ImageView
    private lateinit var tvHuellaPagos: TextView
    private lateinit var tvHuellaPagosEstado: TextView
    private lateinit var swHuellaPagos: MaterialSwitch
    private lateinit var tvCerrarSesion: TextView
    private var cargandoHuella = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_perfil)

        tbPerfil = findViewById(R.id.tbPerfil)
        tvDatosContacto = findViewById(R.id.tvDatosContacto)
        btnEditarContacto = findViewById(R.id.btnEditarContacto)
        cvDatosContacto = findViewById(R.id.cvDatosContacto)
        ivTelefono = findViewById(R.id.ivTelefono)
        tvTelefono = findViewById(R.id.tvTelefono)
        tvTelefonoEtiqueta = findViewById(R.id.tvTelefonoEtiqueta)
        ivCorreo = findViewById(R.id.ivCorreo)
        tvCorreo = findViewById(R.id.tvCorreo)
        tvCorreoEtiqueta = findViewById(R.id.tvCorreoEtiqueta)
        ivDomicilio = findViewById(R.id.ivDomicilio)
        tvDomicilio = findViewById(R.id.tvDomicilio)
        tvDomicilioEtiqueta = findViewById(R.id.tvDomicilioEtiqueta)
        tvConsultorio = findViewById(R.id.tvConsultorio)
        cvConsultorio = findViewById(R.id.cvConsultorio)
        tvNuestrosDoctores = findViewById(R.id.tvNuestrosDoctores)
        tvContactoHorario = findViewById(R.id.tvContactoHorario)
        tvCuenta = findViewById(R.id.tvCuenta)
        cvCuenta = findViewById(R.id.cvCuenta)
        tvCambiarContrasena = findViewById(R.id.tvCambiarContrasena)
        ivHuellaPagos = findViewById(R.id.ivHuellaPagos)
        tvHuellaPagos = findViewById(R.id.tvHuellaPagos)
        tvHuellaPagosEstado = findViewById(R.id.tvHuellaPagosEstado)
        swHuellaPagos = findViewById(R.id.swHuellaPagos)
        tvCerrarSesion = findViewById(R.id.tvCerrarSesion)

        presentador = clsPerfilPacientePresentador(this)
        presentador.CargarPerfil()

        // Listeners de interacciones
        swHuellaPagos.setOnCheckedChangeListener { _, isChecked ->
            if (!cargandoHuella) {
                tvHuellaPagosEstado.text = if (isChecked) "Activada" else "Desactivada"
                presentador.CambiarEstadoHuella(isChecked)
            }
        }

        tvCambiarContrasena.setOnClickListener {
            presentador.ClickCambiarContrasena()
        }

        tvNuestrosDoctores.setOnClickListener {
            presentador.ClickNuestrosDoctores()
        }

        tvContactoHorario.setOnClickListener {
            presentador.ClickContactoHorario()
        }

        tvCerrarSesion.setOnClickListener {
            presentador.ClickCerrarSesion()
        }

        configurarBarraInferior(4)
    }

    fun MostrarInformacion(telefono: String, correo: String, domicilio: String, huellaActiva: Boolean) {
        tvTelefono.text = telefono
        tvCorreo.text = correo
        tvDomicilio.text = domicilio
        cargandoHuella = true
        swHuellaPagos.isChecked = huellaActiva
        cargandoHuella = false
        tvHuellaPagosEstado.text = if (huellaActiva) "Activada" else "Desactivada"
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrACambiarContrasena() {
        val intent = Intent(this, clsRecuperarContrasenaVista::class.java)
        startActivity(intent)
    }

    fun IrANuestrosDoctores() {
        val intent = Intent(this, clsNuestrosDoctoresVista::class.java)
        startActivity(intent)
    }

    fun IrAContactoHorario() {
        val intent = Intent(this, clsContactoVista::class.java)
        startActivity(intent)
    }

    fun CerrarSesionApp() {
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
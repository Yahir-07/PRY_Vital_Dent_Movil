package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsLoginPresentador
import com.example.pry_vital_dent_movil.Modelo.clsBiometria
import com.google.android.material.appbar.MaterialToolbar

class clsLoginVista : AppCompatActivity() {

    private lateinit var presentador: clsLoginPresentador
    private lateinit var biometria: clsBiometria
    private lateinit var tvErrorCredenciales: TextView
    private lateinit var etUsuario: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var btnOlvidasteContrasena: Button
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnIngresarHuella: Button
    private lateinit var btnIrCrearCuenta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        findViewById<MaterialToolbar>(R.id.tbIniciarSesion).mostrarLogo()
        tvErrorCredenciales = findViewById(R.id.tvErrorCredenciales)
        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnOlvidasteContrasena = findViewById(R.id.btnOlvidasteContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnIngresarHuella = findViewById(R.id.btnIngresarHuella)
        btnIrCrearCuenta = findViewById(R.id.btnIrCrearCuenta)

        biometria = clsBiometria(this) // debe crearse aquí, en onCreate
        presentador = clsLoginPresentador(this)

        btnIniciarSesion.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            presentador.ClickIniciarSesion(usuario, contrasena)
        }

        btnOlvidasteContrasena.setOnClickListener {
            presentador.ClickOlvidasteContrasena()
        }

        btnIngresarHuella.setOnClickListener {
            presentador.ClickIngresarHuella()
        }

        btnIrCrearCuenta.setOnClickListener {
            presentador.ClickIrCrearCuenta()
        }

        // Si ya activó la huella, se le pide al abrir (post: espera a que se dibuje la pantalla)
        if (savedInstanceState == null) {
            btnIngresarHuella.post { presentador.AlIniciar() }
        }
    }

    // Métodos que ordenan los cambios visuales
    fun MostrarError(mensaje: String) {
        tvErrorCredenciales.text = mensaje
        tvErrorCredenciales.visibility = View.VISIBLE
    }

    fun OcultarError() {
        tvErrorCredenciales.visibility = View.GONE
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrAlPanelPrincipal() {
        val intent = Intent(this, clsInicioVista::class.java)
        startActivity(intent)
        finish()
    }

    fun IrARecuperarContrasena() {
        val intent = Intent(this, clsRecuperarContrasenaVista::class.java)
        startActivity(intent)
    }

    // ---------- Huella o rostro ----------

    fun BiometriaDisponible(): Boolean = biometria.EstaDisponible()

    fun MotivoBiometriaNoDisponible(): String = biometria.MotivoNoDisponible()

    fun SolicitarHuella(nombre: String) {
        biometria.Autenticar(
            titulo = "Ingresar a Vital Dent",
            subtitulo = if (nombre.isNotEmpty()) "Usa tu huella o tu rostro para entrar como $nombre"
                        else "Usa tu huella o tu rostro",
            alExito = { presentador.HuellaReconocida() },
            alError = { mensaje -> presentador.HuellaNoReconocida(mensaje) }
        )
    }

    fun PreguntarActivarHuella() {
        MaterialAlertDialogBuilder(this)
            .setIcon(R.drawable.ic_huella)
            .setTitle("¿Entrar con tu huella?")
            .setMessage("La próxima vez podrás entrar con tu huella o tu rostro, sin escribir tu contraseña.")
            .setCancelable(false)
            .setPositiveButton("Activar") { _, _ ->
                // Se pide la huella una vez para confirmar que es el dueño del celular
                biometria.Autenticar(
                    titulo = "Activar acceso con huella",
                    subtitulo = "Confirma tu huella o tu rostro",
                    alExito = { presentador.HuellaActivada() },
                    alError = { mensaje ->
                        MostrarMensaje(mensaje)
                        presentador.ActivacionHuellaOmitida()
                    },
                    alCancelar = { presentador.ActivacionHuellaOmitida() }
                )
            }
            .setNegativeButton("Ahora no") { _, _ -> presentador.ActivacionHuellaOmitida() }
            .show()
    }

    fun IrARegistro() {
        val intent = Intent(this, clsRegistroVista::class.java)
        startActivity(intent)
        finish()
    }
}
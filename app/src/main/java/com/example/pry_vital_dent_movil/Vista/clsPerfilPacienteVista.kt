package com.example.pry_vital_dent_movil.Vista

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView.ScaleType
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.materialswitch.MaterialSwitch
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsPerfilPacientePresentador
import com.example.pry_vital_dent_movil.Modelo.clsBiometria
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
    private lateinit var biometria: clsBiometria

    // Cámara (ACC-08)
    private lateinit var ivFotoPerfil: ShapeableImageView
    private lateinit var btnTomarFoto: MaterialButton
    private var uriFoto: Uri? = null
    private var tieneFoto = false

    // Pide el permiso de cámara al sistema
    private val pedirPermisoCamara =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
            presentador.ResultadoPermisoCamara(concedido)
        }

    // Abre la app de cámara; la foto queda en uriFoto
    private val tomarFoto =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { tomada ->
            presentador.ResultadoFotoTomada(tomada, uriFoto)
        }

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
        ivFotoPerfil = findViewById(R.id.ivFotoPerfil)
        btnTomarFoto = findViewById(R.id.btnTomarFoto)

        // Si Android cerró la pantalla mientras la cámara estaba abierta, se recupera el archivo
        uriFoto = savedInstanceState?.getString("uriFoto")?.let { Uri.parse(it) }

        biometria = clsBiometria(this) // debe crearse aquí, en onCreate
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

        btnTomarFoto.setOnClickListener {
            presentador.ClickTomarFoto()
        }

        ivFotoPerfil.setOnClickListener {
            presentador.ClickTomarFoto()
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

    // ---------- Huella para pagos ----------

    fun BiometriaDisponible(): Boolean = biometria.EstaDisponible()

    fun MotivoBiometriaNoDisponible(): String = biometria.MotivoNoDisponible()

    fun ConfirmarHuellaParaActivar() {
        biometria.Autenticar(
            titulo = "Activar huella para pagos",
            subtitulo = "Confirma tu huella o tu rostro",
            alExito = { presentador.HuellaPagosConfirmada() },
            alError = { mensaje -> presentador.HuellaPagosNoConfirmada(mensaje) },
            alCancelar = { presentador.HuellaPagosNoConfirmada(null) },
            mensajeSinIntentos = "Demasiados intentos. No se activó la huella para pagos."
        )
    }

    // Cambia el interruptor sin volver a disparar su listener
    fun MarcarSwitchHuella(activa: Boolean) {
        cargandoHuella = true
        swHuellaPagos.isChecked = activa
        cargandoHuella = false
        tvHuellaPagosEstado.text = if (activa) "Activada" else "Desactivada"
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        uriFoto?.let { outState.putString("uriFoto", it.toString()) }
    }

    // ---------- Cámara: foto de perfil (ACC-08) ----------

    fun TieneCamara(): Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

    fun PermisoCamaraConcedido(): Boolean =
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    fun DebeExplicarPermisoCamara(): Boolean = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)

    fun PedirPermisoCamara() {
        pedirPermisoCamara.launch(Manifest.permission.CAMERA)
    }

    fun MostrarExplicacionPermisoCamara() {
        MaterialAlertDialogBuilder(this)
            .setIcon(R.drawable.ic_camara)
            .setTitle(R.string.camara_permiso_titulo)
            .setMessage(R.string.camara_permiso_explicacion)
            .setPositiveButton(R.string.accion_permitir) { _, _ -> presentador.PermisoExplicadoAceptado() }
            .setNegativeButton(R.string.accion_ahora_no, null)
            .show()
    }

    // Permiso negado con "No volver a preguntar": se manda a los ajustes de la app
    fun MostrarDialogoAjustesCamara() {
        MaterialAlertDialogBuilder(this)
            .setIcon(R.drawable.ic_camara)
            .setTitle(R.string.camara_permiso_titulo)
            .setMessage(R.string.camara_permiso_denegado)
            .setPositiveButton(R.string.accion_abrir_ajustes) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
                startActivity(intent)
            }
            .setNegativeButton(R.string.accion_ahora_no, null)
            .show()
    }

    fun AbrirCamara(destino: Uri) {
        uriFoto = destino
        try {
            tomarFoto.launch(destino)
        } catch (e: Exception) {
            MostrarMensaje("No se encontró una app de cámara en este celular")
        }
    }

    fun MostrarFoto(foto: Bitmap) {
        tieneFoto = true
        ivFotoPerfil.setPadding(0, 0, 0, 0)
        ivFotoPerfil.scaleType = ScaleType.CENTER_CROP
        ivFotoPerfil.setImageBitmap(foto)
        btnTomarFoto.setText(R.string.accion_cambiar_foto)
    }

    fun MostrarSubiendoFoto(subiendo: Boolean) {
        btnTomarFoto.isEnabled = !subiendo
        ivFotoPerfil.alpha = if (subiendo) 0.5f else 1f
        when {
            subiendo -> btnTomarFoto.text = "Guardando foto…"
            tieneFoto -> btnTomarFoto.setText(R.string.accion_cambiar_foto)
            else -> btnTomarFoto.setText(R.string.accion_tomar_foto)
        }
    }
}

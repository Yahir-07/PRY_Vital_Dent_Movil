package com.example.pry_vital_dent_movil.Vista

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsCambiarContrasenaPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsCambiarContrasenaVista : AppCompatActivity() {

    private lateinit var presentador: clsCambiarContrasenaPresentador
    private lateinit var tbCambiarContrasena: MaterialToolbar
    private lateinit var tilContrasenaActual: TextInputLayout
    private lateinit var etContrasenaActual: TextInputEditText
    private lateinit var tilContrasenaNueva: TextInputLayout
    private lateinit var etContrasenaNueva: TextInputEditText
    private lateinit var cvRequisitos: MaterialCardView
    private lateinit var tvRequisitoLongitud: TextView
    private lateinit var tvRequisitoMayuscula: TextView
    private lateinit var tvRequisitoNumero: TextView
    private lateinit var tvRequisitoDistinta: TextView
    private lateinit var tilConfirmarContrasenaNueva: TextInputLayout
    private lateinit var etConfirmarContrasenaNueva: TextInputEditText
    private lateinit var btnCancelar: MaterialButton
    private lateinit var btnGuardarCambios: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasena)

        tbCambiarContrasena = findViewById(R.id.tbCambiarContrasena)
        tilContrasenaActual = findViewById(R.id.tilContrasenaActual)
        etContrasenaActual = findViewById(R.id.etContrasenaActual)
        tilContrasenaNueva = findViewById(R.id.tilContrasenaNueva)
        etContrasenaNueva = findViewById(R.id.etContrasenaNueva)
        cvRequisitos = findViewById(R.id.cvRequisitos)
        tvRequisitoLongitud = findViewById(R.id.tvRequisitoLongitud)
        tvRequisitoMayuscula = findViewById(R.id.tvRequisitoMayuscula)
        tvRequisitoNumero = findViewById(R.id.tvRequisitoNumero)
        tvRequisitoDistinta = findViewById(R.id.tvRequisitoDistinta)
        tilConfirmarContrasenaNueva = findViewById(R.id.tilConfirmarContrasenaNueva)
        etConfirmarContrasenaNueva = findViewById(R.id.etConfirmarContrasenaNueva)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        tbCambiarContrasena.mostrarLogo()

        presentador = clsCambiarContrasenaPresentador(this)

        btnGuardarCambios.setOnClickListener {
            val actual = etContrasenaActual.text.toString().trim()
            val nueva = etContrasenaNueva.text.toString().trim()
            val confirmar = etConfirmarContrasenaNueva.text.toString().trim()
            presentador.ClickGuardarCambios(actual, nueva, confirmar)
        }

        btnCancelar.setOnClickListener {
            presentador.ClickCancelar()
        }
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun RegresarAlPerfil() {
        finish()
    }
}
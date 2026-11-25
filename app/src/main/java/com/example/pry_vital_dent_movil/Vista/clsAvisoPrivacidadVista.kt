package com.example.pry_vital_dent_movil.Vista

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsAvisoPrivacidadPresentador


class clsAvisoPrivacidadVista(
    private val actividad: AppCompatActivity,
    private val alAceptar: () -> Unit
) {

    private val presentador = clsAvisoPrivacidadPresentador(this, actividad)
    private var dialogo: AlertDialog? = null
    private var btnAceptar: MaterialButton? = null

    fun Mostrar() {
        if (dialogo?.isShowing == true) return

        val vista = actividad.layoutInflater.inflate(R.layout.dialogo_aviso_privacidad, null)
        val cbAcepto = vista.findViewById<MaterialCheckBox>(R.id.cbAceptoAvisoBurbuja)
        val btnCerrar = vista.findViewById<ImageButton>(R.id.btnCerrarAvisoPrivacidad)
        btnAceptar = vista.findViewById(R.id.btnAceptarAvisoPrivacidad)

        cbAcepto.setOnCheckedChangeListener { _, marcada -> presentador.CasillaCambiada(marcada) }
        btnAceptar?.setOnClickListener { presentador.ClickAceptar() }
        btnCerrar.setOnClickListener { presentador.ClickCerrar() }

        dialogo = MaterialAlertDialogBuilder(actividad)
            .setView(vista)
            .create()
            .apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }

        HabilitarAceptar(false)
    }

    fun HabilitarAceptar(habilitar: Boolean) {
        btnAceptar?.isEnabled = habilitar
        btnAceptar?.alpha = if (habilitar) 1f else 0.5f
    }

    fun Cerrar() {
        dialogo?.dismiss()
        dialogo = null
    }

    fun NotificarAceptado() {
        alAceptar()
    }
}

fun CheckBox.conectarAvisoPrivacidad(actividad: AppCompatActivity) {
    val burbuja = clsAvisoPrivacidadVista(actividad) { isChecked = true }

    setOnClickListener {
        if (isChecked) {
            isChecked = false
            burbuja.Mostrar()
        }
    }
}

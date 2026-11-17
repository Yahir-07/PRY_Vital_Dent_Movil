package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsBienvenidoPresentador
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var presentador: clsBienvenidoPresentador
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnCrearCuenta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bienvenida)

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)

        presentador = clsBienvenidoPresentador(this)
        presentador.VerificarSesion()

        btnIniciarSesion.setOnClickListener {
            presentador.ClickIniciarSesion()
        }

        btnCrearCuenta.setOnClickListener {
            presentador.ClickCrearCuenta()
        }
    }

    fun IrALogin() {
        startActivity(Intent(this, clsLoginVista::class.java))
    }

    fun IrARegistro() {
        startActivity(Intent(this, clsRegistroVista::class.java))
    }

    fun IrAlInicio() {
        startActivity(Intent(this, clsInicioVista::class.java))
        finish()
    }
}
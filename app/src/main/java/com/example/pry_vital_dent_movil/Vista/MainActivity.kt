package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsBienvenidoPresentador
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var presentador: clsBienvenidoPresentador
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnCrearCuenta: Button
    private lateinit var ivLogo: ImageView
    private lateinit var ivBienvenida: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bienvenida)

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        ivLogo = findViewById(R.id.ivLogo)
        ivBienvenida = findViewById(R.id.ivBienvenida)

        presentador = clsBienvenidoPresentador(this)
        presentador.VerificarSesion()
        presentador.CargarImagenes()

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
    fun MostrarImagenes(urlLogo: String?, urlFoto: String?) {
        ivLogo.cargarImagen(urlLogo, R.drawable.img_logo_vital_dent)
        ivBienvenida.cargarImagen(urlFoto, R.drawable.img_bienvenida)
    }
}
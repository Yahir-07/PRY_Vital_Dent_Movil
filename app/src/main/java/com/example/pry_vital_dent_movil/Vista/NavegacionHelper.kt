package com.example.pry_vital_dent_movil.Vista

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.pry_vital_dent_movil.R

fun AppCompatActivity.configurarBarraInferior(indiceActual: Int) {
    val bnv = findViewById<BottomNavigationView>(R.id.bnvNavegacion) ?: return

    bnv.selectedItemId = bnv.menu.getItem(indiceActual).itemId

    bnv.setOnItemSelectedListener { item ->
        val destino = when (item.itemId) {
            R.id.navInicio -> if (indiceActual != 0) clsInicioVista::class.java else null
            R.id.navCitas -> if (indiceActual != 1) clsMisCitasVista::class.java else null
            R.id.navTratamientos -> if (indiceActual != 2) clsMisTratamientosVista::class.java else null
            R.id.navPagos -> if (indiceActual != 3) clsPagosVista::class.java else null
            R.id.navPerfil -> if (indiceActual != 4) clsPerfilPacienteVista::class.java else null
            else -> null
        }

        if (destino != null) {
            val intent = Intent(this, destino)
            startActivity(intent)
            overridePendingTransition(0, 0) // <-- Esto elimina por completo la animación de salida/entrada
            finish()
            true
        } else {
            true
        }
    }
}
package com.example.pry_vital_dent_movil.Vista

import android.app.Activity
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pry_vital_dent_movil.Modelo.clsImagenesModelo
import com.example.pry_vital_dent_movil.R
import com.google.android.material.appbar.MaterialToolbar
import android.view.View
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition
import android.graphics.drawable.InsetDrawable

fun ImageView.cargarImagen(url: String?, @DrawableRes marcador: Int) {
    if (pantallaCerrada()) return

    Glide.with(this)
        .load(url?.ifBlank { null })
        .placeholder(marcador)
        .error(marcador)
        .fallback(marcador)
        .transition(DrawableTransitionOptions.with(
            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        ))
        .into(this)
}
fun MaterialToolbar.mostrarLogo() {
    clsImagenesModelo.ObtenerImagenes(context) { imagenes ->
        val url = imagenes[clsImagenesModelo.LOGO] ?: return@ObtenerImagenes
        if (pantallaCerrada()) return@ObtenerImagenes

        val tamano = resources.getDimensionPixelSize(R.dimen.vd_logo_encabezado)
        Glide.with(this)
            .load(url)
            .fitCenter()
            .into(object : CustomTarget<Drawable>(tamano, tamano) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val espacio = resources.getDimensionPixelSize(R.dimen.vd_espacio_xs2)
                    logo = InsetDrawable(resource, 0, 0, espacio, 0)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    logo = placeholder
                }
            })
    }
}

private fun View.pantallaCerrada(): Boolean {
    var ctx = context
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx.isFinishing || ctx.isDestroyed
        ctx = ctx.baseContext
    }
    return false
}



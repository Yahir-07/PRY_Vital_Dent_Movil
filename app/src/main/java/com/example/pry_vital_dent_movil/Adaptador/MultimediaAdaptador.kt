package com.example.pry_vital_dent_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pry_vital_dent_movil.Modelo.DataMultimedia
import com.example.pry_vital_dent_movil.R

class MultimediaAdaptador(
    private var listaMultimedia: List<DataMultimedia>,
    private val onSeleccionar: (DataMultimedia) -> Unit
) : RecyclerView.Adapter<MultimediaAdaptador.MultimediaViewHolder>() {

    class MultimediaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMiniatura: ImageView = view.findViewById(R.id.ivMultimediaMiniatura)
        val tvDuracion: TextView = view.findViewById(R.id.tvMultimediaDuracion)
        val tvTipo: TextView = view.findViewById(R.id.tvMultimediaTipo)
        val tvTitulo: TextView = view.findViewById(R.id.tvMultimediaTitulo)
        val tvDescripcion: TextView = view.findViewById(R.id.tvMultimediaDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultimediaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_multimedia, parent, false)
        return MultimediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MultimediaViewHolder, position: Int) {
        val elemento = listaMultimedia[position]
        val contexto = holder.itemView.context

        holder.tvTitulo.text = elemento.titulo
        holder.tvDescripcion.text = elemento.descripcion
        holder.tvDuracion.text = elemento.duracion
        holder.tvDuracion.visibility = if (elemento.duracion.isBlank()) View.GONE else View.VISIBLE
        holder.tvTipo.text = contexto.getString(
            if (elemento.esVideo) R.string.multimedia_tipo_video else R.string.multimedia_tipo_audio
        )

        // 4.1 · Imagen remota del hosting con Glide:
        // caché en memoria y disco, marcador mientras carga, imagen de error,
        // recorte centrado + bordes redondeados y transición suave.
        val radio = contexto.resources.getDimensionPixelSize(R.dimen.vd_radio_m)
        Glide.with(contexto)
            .load(elemento.urlMiniatura.ifBlank { null })
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.bg_placeholder)
            .error(R.drawable.img_multimedia_error)
            .fallback(R.drawable.img_multimedia_error)
            .transform(CenterCrop(), RoundedCorners(radio))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.ivMiniatura)

        holder.itemView.setOnClickListener { onSeleccionar(elemento) }
    }

    override fun onViewRecycled(holder: MultimediaViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.itemView.context).clear(holder.ivMiniatura)
    }

    override fun getItemCount(): Int = listaMultimedia.size

    fun actualizarLista(nuevaLista: List<DataMultimedia>) {
        listaMultimedia = nuevaLista
        notifyDataSetChanged()
    }
}

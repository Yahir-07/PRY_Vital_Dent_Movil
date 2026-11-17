package com.example.pry_vital_dent_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataAvance
import com.example.pry_vital_dent_movil.R

class AvanceAdaptador(
    private var listaAvances: List<DataAvance>,
    private val onSeleccionarAvance: (DataAvance) -> Unit
) : RecyclerView.Adapter<AvanceAdaptador.AvanceViewHolder>() {

    class AvanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvancePunto: ImageView = view.findViewById(R.id.ivAvancePunto)
        val tvAvanceSesion: TextView = view.findViewById(R.id.tvAvanceSesion)
        val tvAvanceFecha: TextView = view.findViewById(R.id.tvAvanceFecha)
        val tvAvanceDescripcion: TextView = view.findViewById(R.id.tvAvanceDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_avance, parent, false)
        return AvanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvanceViewHolder, position: Int) {
        val avance = listaAvances[position]

        holder.tvAvanceSesion.text = avance.sesion
        holder.tvAvanceFecha.text = avance.fecha
        holder.tvAvanceDescripcion.text = avance.descripcion

        holder.itemView.setOnClickListener {
            onSeleccionarAvance(avance)
        }
    }

    override fun getItemCount(): Int = listaAvances.size

    fun actualizarLista(nuevaLista: List<DataAvance>) {
        listaAvances = nuevaLista
        notifyDataSetChanged()
    }
}
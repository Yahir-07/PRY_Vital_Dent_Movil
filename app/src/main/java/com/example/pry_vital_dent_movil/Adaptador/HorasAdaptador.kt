package com.example.pry_vital_dent_movil.Adaptador

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataHora
import com.example.pry_vital_dent_movil.R

// Casillas de horario (item_hora) en rvHoras: libre, elegida (isSelected) u ocupada (isEnabled = false, tachada)
class HorasAdaptador(
    private var listaHoras: List<DataHora>,
    private val onSeleccionarHora: (DataHora) -> Unit
) : RecyclerView.Adapter<HorasAdaptador.HoraViewHolder>() {

    private var posicionSeleccionada = RecyclerView.NO_POSITION

    class HoraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHora: TextView = view.findViewById(R.id.tvHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoraViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hora, parent, false)
        return HoraViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoraViewHolder, position: Int) {
        val hora = listaHoras[position]

        holder.tvHora.text = hora.texto
        holder.tvHora.isEnabled = hora.disponible
        holder.tvHora.isSelected = position == posicionSeleccionada
        holder.tvHora.paintFlags = if (hora.disponible) {
            holder.tvHora.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        } else {
            holder.tvHora.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.tvHora.setOnClickListener {
            val actual = holder.bindingAdapterPosition
            if (actual == RecyclerView.NO_POSITION || !listaHoras[actual].disponible) return@setOnClickListener

            val anterior = posicionSeleccionada
            posicionSeleccionada = actual
            if (anterior != RecyclerView.NO_POSITION) notifyItemChanged(anterior)
            notifyItemChanged(actual)

            onSeleccionarHora(listaHoras[actual])
        }
    }

    override fun getItemCount(): Int = listaHoras.size

    fun actualizarLista(nuevaLista: List<DataHora>) {
        listaHoras = nuevaLista
        posicionSeleccionada = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}

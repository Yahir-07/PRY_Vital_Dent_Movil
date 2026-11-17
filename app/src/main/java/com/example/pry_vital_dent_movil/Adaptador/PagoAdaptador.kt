package com.example.pry_vital_dent_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataPago
import com.example.pry_vital_dent_movil.R

class PagoAdaptador(
    private var listaPagos: List<DataPago>,
    private val onSeleccionarPago: (DataPago) -> Unit
) : RecyclerView.Adapter<PagoAdaptador.PagoViewHolder>() {

    class PagoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPagoIcono: ImageView = view.findViewById(R.id.ivPagoIcono)
        val tvPagoTitulo: TextView = view.findViewById(R.id.tvPagoTitulo)
        val tvPagoDetalle: TextView = view.findViewById(R.id.tvPagoDetalle)
        val tvPagoEstado: TextView = view.findViewById(R.id.tvPagoEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pago, parent, false)
        return PagoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagoViewHolder, position: Int) {
        val pago = listaPagos[position]

        holder.tvPagoTitulo.text = pago.concepto
        holder.tvPagoDetalle.text = pago.detalle
        holder.tvPagoEstado.text = pago.estado

        // Aplicar fondo según el estado del pago (como se indica en tu XML)
        when (pago.estado.lowercase()) {
            "aprobado" -> holder.tvPagoEstado.setBackgroundResource(R.drawable.bg_estado_exito)
            "rechazado" -> holder.tvPagoEstado.setBackgroundResource(R.drawable.bg_estado_error)
            else -> holder.tvPagoEstado.setBackgroundResource(R.drawable.bg_estado)
        }

        holder.itemView.setOnClickListener {
            onSeleccionarPago(pago)
        }
    }

    override fun getItemCount(): Int = listaPagos.size

    fun actualizarLista(nuevaLista: List<DataPago>) {
        listaPagos = nuevaLista
        notifyDataSetChanged()
    }
}
package com.myproj.alurarecycletview.ui.recyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.myproj.alurarecycletview.R
import com.myproj.alurarecycletview.model.Nota
import com.myproj.alurarecycletview.ui.recyclerView.listener.OnItemClickListener
import java.util.*

class ListaNotasAdapter(var notas: MutableList<Nota>): RecyclerView.Adapter<ListaNotasAdapter.ViewHolder>(){

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaNotasAdapter.ViewHolder {
        var viewCriada = LayoutInflater.from(parent.context).inflate(R.layout.item_nota, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ListaNotasAdapter.ViewHolder, position: Int) {
        val nota = notas.get(position)
        holder.vincula(nota)
    }

    override fun getItemCount(): Int = notas.size

    fun adiciona(nota: Nota){
        notas.add(nota)
        notifyDataSetChanged()
    }

    fun altera(posicao: Int, nota: Nota) {
        notas.set(posicao, nota)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        notas.removeAt(position)
        notifyItemRemoved(position)
    }

    fun troca(posicaoInicial: Int, posicaoFinal: Int) {
        Collections.swap(notas, posicaoInicial, posicaoFinal)
        notifyItemMoved(posicaoInicial, posicaoFinal)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titulo = itemView.findViewById<TextView>(R.id.item_nota_titulo)
        var descricao = itemView.findViewById<TextView>(R.id.item_nota_descricao)
        private lateinit var nota: Nota

        init {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(nota, adapterPosition)
            }
        }

        fun vincula(nota: Nota){
            this.nota = nota
            preencheCampo()
        }

        fun preencheCampo(){
            titulo.text = nota.titulo
            descricao.text = nota.descricao
        }
    }

}
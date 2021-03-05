package com.myproj.alurarecycletview.ui.recyclerView.helper.callback

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.myproj.alurarecycletview.model.NotaDAO
import com.myproj.alurarecycletview.ui.recyclerView.adapter.ListaNotasAdapter

class NotaItemTouchCallBack(private val myAdapter: ListaNotasAdapter) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val marcacoesDeslize = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        val marcacoesArrastar = ItemTouchHelper.DOWN or ItemTouchHelper.UP or
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        return makeMovementFlags(marcacoesArrastar, marcacoesDeslize)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val posicaoInicial = viewHolder.adapterPosition
        val posicaoFinal = target.adapterPosition
        trocaNotas(posicaoInicial, posicaoFinal)
        return true
    }

    private fun trocaNotas(posicaoInicial: Int, posicaoFinal: Int) {
        NotaDAO().troca(posicaoInicial, posicaoFinal)
        myAdapter.troca(posicaoInicial, posicaoFinal)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val adapterPosition = viewHolder.adapterPosition
        removeNota(adapterPosition)
    }

    private fun removeNota(adapterPosition: Int) {
        NotaDAO().remove(adapterPosition)
        myAdapter.remove(adapterPosition)
    }

}

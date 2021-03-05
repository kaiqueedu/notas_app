package com.myproj.alurarecycletview.ui.recyclerView.listener

import com.myproj.alurarecycletview.model.Nota

interface OnItemClickListener {

    fun onItemClick(nota: Nota, posicao: Int)
}
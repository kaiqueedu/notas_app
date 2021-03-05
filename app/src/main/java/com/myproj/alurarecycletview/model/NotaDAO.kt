package com.myproj.alurarecycletview.model

import java.util.*

class NotaDAO{

    companion object{
        var notas: MutableList<Nota> = mutableListOf()
    }

    fun insere(notas: Nota){
        NotaDAO.notas.add(notas)
    }

    fun altera(posicao: Int, nota: Nota){
        notas.set(posicao, nota)
    }

    fun remove(posicao: Int){
        notas.removeAt(posicao)
    }

    fun troca(posicaoIni: Int, posicaoFim: Int){
        Collections.swap(notas, posicaoIni, posicaoFim)
    }

    fun todos(): MutableList<Nota> {
        return NotaDAO.notas
    }

    fun revemoTodas(){
        notas.clear()
    }

}
package com.myproj.alurarecycletview.ui.activity

interface NotaActivityConstantes {

    companion object {

        val INSERE_NOTA: Int
            get() = 1

        val ALTERA_NOTA: Int
            get() = 2

        val CHAVE_NOTA: String
            get() = "nota"

        val CHAVE_POSICAO: String
            get() = "posicao"

        val POSICAO_INVALIDA: Int
            get() = -1
    }
}
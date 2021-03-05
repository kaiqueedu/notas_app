package com.myproj.alurarecycletview.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myproj.alurarecycletview.R
import com.myproj.alurarecycletview.model.Nota
import com.myproj.alurarecycletview.model.NotaDAO
import com.myproj.alurarecycletview.ui.activity.NotaActivityConstantes.Companion.ALTERA_NOTA
import com.myproj.alurarecycletview.ui.activity.NotaActivityConstantes.Companion.CHAVE_NOTA
import com.myproj.alurarecycletview.ui.activity.NotaActivityConstantes.Companion.CHAVE_POSICAO
import com.myproj.alurarecycletview.ui.activity.NotaActivityConstantes.Companion.INSERE_NOTA
import com.myproj.alurarecycletview.ui.activity.NotaActivityConstantes.Companion.POSICAO_INVALIDA
import com.myproj.alurarecycletview.ui.recyclerView.adapter.ListaNotasAdapter
import com.myproj.alurarecycletview.ui.recyclerView.helper.callback.NotaItemTouchCallBack
import com.myproj.alurarecycletview.ui.recyclerView.listener.OnItemClickListener

class ListaNotasActivity : AppCompatActivity() {

    private lateinit var myAdapter: ListaNotasAdapter
    private var todasNotas: MutableList<Nota> = mutableListOf()
    private var dao = NotaDAO()

    companion object{
        val TITULO_APPBAR = "Notas"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_notas_activity)
        title = TITULO_APPBAR

        //todasNotas.addAll(dao.todos())
        todasNotas.addAll(pegaNotas())
        configuraRecyclerView(todasNotas)
        configuraInsereNota()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun pegaNotas(): MutableList<Nota>{
       return dao.todos()
    }

    private fun configuraRecyclerView(todasNotas: MutableList<Nota>) {
        var listaNotasView = findViewById<RecyclerView>(R.id.lista_notas_RecyclerView)
        configuraAdapter(todasNotas, listaNotasView)
        configuraItemTouchHelper(listaNotasView)
    }

    private fun configuraItemTouchHelper(listaNotasView: RecyclerView?) {
        var itemTouchHelper = ItemTouchHelper(NotaItemTouchCallBack(myAdapter))
        itemTouchHelper.attachToRecyclerView(listaNotasView)
    }

    private fun configuraAdapter(todasNotas: MutableList<Nota>, listaNotasView: RecyclerView) {
        myAdapter = ListaNotasAdapter(todasNotas)
        listaNotasView.adapter = myAdapter
        myAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(nota: Nota, posicao: Int) {
                enviarParaFormAlterar(nota, posicao)
            }
        })
    }

    private fun enviarParaFormAlterar(nota: Nota, posicao: Int) {
        var intent = Intent(this@ListaNotasActivity, FormularioNotaActivity::class.java)
        intent.putExtra(CHAVE_NOTA, nota)
        intent.putExtra(CHAVE_POSICAO, posicao)
        startActivityForResult(intent, ALTERA_NOTA)
    }

    private fun configuraInsereNota() {
        var inserirNota = findViewById<TextView>(R.id.lista_nota_insere_nota)
        inserirNota.setOnClickListener {
            enviarParaFormularioInserir()
        }
    }

    private fun enviarParaFormularioInserir() {
        val intent = Intent(this@ListaNotasActivity, FormularioNotaActivity::class.java)
        startActivityForResult(intent, INSERE_NOTA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(isRequestInsereNota(requestCode, data)){
            if(resultadoOK(resultCode)) {
                adicionaNotaRecebida(data)
            }else if(resultCode == Activity.RESULT_CANCELED){
                //fazer alguma ação
            }
        }
        if(isResquestAlteraNota(requestCode, data)) {
            if (resultadoOK(resultCode)) {
                var notaRecebida = data?.getSerializableExtra(CHAVE_NOTA) as Nota
                var posicaoRecebida = data?.getSerializableExtra(CHAVE_POSICAO) as Int
                if (posicaoRecebida > POSICAO_INVALIDA) {
                    alteraNota(posicaoRecebida, notaRecebida)
                } else {
                    Toast.makeText(this, "Erro na alteração da nota", Toast.LENGTH_SHORT).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //fazer alguma ação
            }
        }
    }

    private fun alteraNota(posicao: Int, nota: Nota) {
        dao.altera(posicao, nota)
        myAdapter.altera(posicao, nota)
    }

    private fun adicionaNotaRecebida(data: Intent?) {
        var novaNota = data?.getSerializableExtra(CHAVE_NOTA) as Nota
        NotaDAO().insere(novaNota)
        myAdapter.adiciona(novaNota)
    }

    private fun isRequestInsereNota(
        requestCode: Int,
        data: Intent?
    ) = isRequestInsereNota(requestCode) && hasDataNota(data)

    private fun isResquestAlteraNota(
        requestCode: Int,
        data: Intent?
    ) = IsRequestAlteraNota(requestCode) && hasDataNota(data)

    private fun IsRequestAlteraNota(requestCode: Int) = requestCode == ALTERA_NOTA

    private fun isRequestInsereNota(requestCode: Int) = requestCode == INSERE_NOTA

    private fun resultadoOK(resultCode: Int) = resultCode == Activity.RESULT_OK

    private fun hasDataNota(data: Intent?): Boolean {
        if(data == null){
            return false
        }
        return data.hasExtra(CHAVE_NOTA)
    }

    fun myCallBack(nota: Nota){

    }

}
package com.myproj.alurarecycletview.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.myproj.alurarecycletview.R
import com.myproj.alurarecycletview.model.Nota
import com.myproj.alurarecycletview.ui.activity.NotaActivityConstantes.Companion.CHAVE_NOTA
import com.myproj.alurarecycletview.ui.activity.NotaActivityConstantes.Companion.CHAVE_POSICAO
import com.myproj.alurarecycletview.ui.activity.NotaActivityConstantes.Companion.POSICAO_INVALIDA

class FormularioNotaActivity : AppCompatActivity() {


    private var posicaoRecebida: Int = POSICAO_INVALIDA

    private lateinit var titulo: EditText
    private lateinit var descricao: EditText

    companion object{
        val TITULO_APP_INSERIR = "Inserir nota"
        val TITULO_APP_ALTERAR = "Altera nota"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_nota)
        title = TITULO_APP_INSERIR

        inicializaCampos()

        var dadosRecebidos = intent
        if(dadosRecebidos.hasExtra(CHAVE_NOTA)){
            title = TITULO_APP_ALTERAR
            val notaRecebida = dadosRecebidos.getSerializableExtra(CHAVE_NOTA) as Nota
            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA)

            preencheCampos(notaRecebida)
        }

    }

    private fun preencheCampos(notaRecebida: Nota) {
        titulo.setText(notaRecebida.titulo)
        descricao.setText(notaRecebida.descricao)
    }

    private fun inicializaCampos() {
        titulo = findViewById<EditText>(R.id.formulario_nota_titulo)
        descricao = findViewById<EditText>(R.id.formulario_nota_descricao)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_formulario_nota, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(isMenuSalvaNota(item)){
            var novaNota = criaNota()
            //ver como funciona o parcelable
            retornaNova(novaNota)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun retornaNova(novaNota: Nota) {
        var retornoInsercao = Intent()
        retornoInsercao.putExtra(CHAVE_NOTA, novaNota)
        retornoInsercao.putExtra(CHAVE_POSICAO, posicaoRecebida)
        setResult(Activity.RESULT_OK, retornoInsercao)
    }

    private fun criaNota(): Nota {
        titulo = findViewById<EditText>(R.id.formulario_nota_titulo)
        descricao = findViewById<EditText>(R.id.formulario_nota_descricao)
        var novaNota = Nota(titulo.text.toString(), descricao.text.toString())
        return novaNota
    }

    private fun isMenuSalvaNota(item: MenuItem) = item.itemId == R.id.menu_form_nota_salva

}
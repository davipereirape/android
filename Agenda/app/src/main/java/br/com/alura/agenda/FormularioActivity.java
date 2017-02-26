package br.com.alura.agenda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // inicia o helper que será utilizado para fazer o merge entre a view e a classe aluno.
        helper = new FormularioHelper(this);

        // Se a activity for chamada de outra tela e a tela passar algum parâmetro, o mesmo é recuperado.
        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null)
        {
            helper.preencheFormulario(aluno);
        }

        Button botaoSalvar = (Button) findViewById(R.id.formulario_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trataAcao();
            }
        });
    }

    // Cria a tela de menu de cima
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Ação dos itens da tela, quando clicados.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_formulario:
                trataAcao();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void trataAcao()
    {
        Aluno aluno = helper.getAluno();
        AlunoDAO alunoDAO = new AlunoDAO(FormularioActivity.this, null);

        if (aluno.getId() != null)
        {
            alunoDAO.altera(aluno);
        }
        else
        {
            alunoDAO.insere(aluno);
        }
        alunoDAO.close();

        Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " Salvo!", Toast.LENGTH_SHORT).show();
        // Encerra a activity
        finish();
    }


}

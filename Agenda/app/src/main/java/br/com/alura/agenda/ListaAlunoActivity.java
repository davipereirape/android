package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.alura.agenda.modelo.Aluno;
import java.util.*;

import br.com.alura.agenda.dao.AlunoDAO;

public class ListaAlunoActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adiciona a view...
        setContentView(R.layout.activity_lista_aluno);

    }

    // ATENTAR AO CICLO DE VIDA DA ACTIVITY
    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    // criação do menu de quando seguramos um item de uma lista.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

                AlunoDAO dao = new AlunoDAO(ListaAlunoActivity.this, null);
                dao.deleta(aluno);
                dao.close();

                Toast.makeText(ListaAlunoActivity.this, "O Aluno " + aluno.getNome() + " foi deletado!", Toast.LENGTH_SHORT).show();
                carregaLista();
                return false;
            }
        } );
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this, null);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        listaAlunos = (ListView) findViewById(R.id.listaAlunos);
        // conversão do arrayString para ListView... quem solicita, como será exibido, qual o layout textview, etc, lista de alunos os dados para conversão.
        ArrayAdapter<Aluno> arrayAdapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);

        listaAlunos.setAdapter(arrayAdapter);

        // Menu quando seguramos a lista.
        registerForContextMenu(listaAlunos);

        // Evento de click em um item da lista
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent intentVaiParaFormAlulo = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                intentVaiParaFormAlulo.putExtra("aluno", aluno);

                startActivity(intentVaiParaFormAlulo);
            }
        }
        );

        Button botaoNovoAluno = (Button) findViewById(R.id.novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });
    }
}

package br.com.alura.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by Davi Pereira on 12/02/2017.
 */
public class FormularioHelper {

    private final EditText editTextNome;
    private final EditText editTextEndereco;
    private final EditText editTextTelefone;
    private final EditText editTextSite;
    private final RatingBar ratingBar;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity)
    {
         editTextNome = (EditText) activity.findViewById(R.id.formulario_nome);
         editTextEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
         editTextTelefone= (EditText) activity.findViewById(R.id.formulario_telefone);
         editTextSite = (EditText) activity.findViewById(R.id.formulario_site);
         ratingBar = (RatingBar) activity.findViewById(R.id.formulario_nota);
    }

    public Aluno getAluno()
    {
        aluno = new Aluno();
        aluno.setNome(editTextNome.getText().toString());
        aluno.setEndereco(editTextEndereco.getText().toString());
        aluno.setNota(Double.valueOf(ratingBar.getRating()));
        aluno.setSite(editTextSite.getText().toString());
        aluno.setTelefone(editTextTelefone.getText().toString());

        return aluno;
    }
}

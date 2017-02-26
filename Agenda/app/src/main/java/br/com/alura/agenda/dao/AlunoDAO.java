package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Classe de persistência dos alunos onde o banco de dados nativo, SQLLite é utilizado.
 *
 * Created by Davi Pereira on 12/02/2017.
 */
public class AlunoDAO extends SQLiteOpenHelper
{

    /**
     * Contrutor padrão.
     * @param context
     * @param errorHandler
     */
    public AlunoDAO(Context context, DatabaseErrorHandler errorHandler)
    {
        super(context, "Agenda", null, 1, errorHandler);
    }

    /**
     * Responsável por criar tabela, caso a mesma não exista no celular do utilizador.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE aluno (id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL," +
                "endereco TEXT," +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL );";

        db.execSQL(sql);
    }

    /**
     * Utilizado para rodar comandos para atualização de banco de dados, utiliza as versões antigas e novas para
     * conseguir realizar a modificação.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Método para inserção de aluno.
     * @param aluno
     */
    public void insere(Aluno aluno)
    {
        SQLiteDatabase db = getWritableDatabase(); // referencia de base que eu posso escrever.

        ContentValues dados = getContentValues(aluno);

        db.insert("aluno", null, dados);
    }

    /**
     * Método de busca de alunos.
     * @return List de alunos.
     */
    public List<Aluno> buscaAlunos()
    {
        String sql = "SELECT * FROM aluno;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<Aluno> listaAluno = new ArrayList<Aluno>();
        while (cursor.moveToNext())
        {
            Aluno aluno = new Aluno();
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));

            listaAluno.add(aluno);
        }
        cursor.close();

        return listaAluno;
    }

    /**
     * Método utilizado para apagar os alunos.
     * @param aluno
     */
    public void deleta(Aluno aluno)
    {
        SQLiteDatabase db = getReadableDatabase();
        String [] params = new String[1];
        params[0] = aluno.getId().toString();
        db.delete("aluno", "id = ?", params);
    }

    /**
     * Realiza alteração de aluno.
     * @param aluno
     */
    public void altera(Aluno aluno)
    {
        SQLiteDatabase db = getWritableDatabase(); // referencia de base que eu posso escrever.
        String [] params = new String[1];

        params[0] = aluno.getId().toString();
        ContentValues dados = getContentValues(aluno);
        db.update("aluno", dados, "id = ?", params);
    }

    /**
     * Cria um contentValues com os valores do aluno.
     * @param aluno
     * @return
     */
    @NonNull
    private ContentValues getContentValues(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        return dados;
    }
}

package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by Davi Pereira on 12/02/2017.
 */
public class AlunoDAO extends SQLiteOpenHelper
{

    public AlunoDAO(Context context, DatabaseErrorHandler errorHandler)
    {
        super(context, "Agenda", null, 1, errorHandler);
    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insere(Aluno aluno)
    {
        SQLiteDatabase db = getWritableDatabase(); // referencia de base que eu posso escrever.

        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());

        db.insert("aluno", null, dados);
    }

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

    public void deleta(Aluno aluno)
    {
        SQLiteDatabase db = getReadableDatabase();
        String [] params = new String[1];
        params[0] = aluno.getId().toString();
        db.delete("aluno", "id = ?", params);

    }
}

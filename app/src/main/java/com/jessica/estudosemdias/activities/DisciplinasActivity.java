package com.jessica.estudosemdias.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jessica.estudosemdias.Adapters.ListaDisciplinasAdapter;
import com.jessica.estudosemdias.Model.Disciplina;
import com.jessica.estudosemdias.Model.Disciplina_;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class DisciplinasActivity extends AppCompatActivity {


    @BindView(R.id.rv_lista_disciplinas) protected RecyclerView recyclerDisciplinas;

    private Box<Disciplina> disciplinaBox;
    private long idUsuarioLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);



        ButterKnife.bind(this);

        disciplinaBox = ((App)getApplication()).getBoxStore().boxFor(Disciplina.class);
        idUsuarioLogado = getidUsuarioLogado();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Disciplina> disciplinas = disciplinaBox.query().equal(Disciplina_.alunoId, idUsuarioLogado).build().find();
        ListaDisciplinasAdapter adapter = new ListaDisciplinasAdapter(this, disciplinas, disciplinaBox);

        recyclerDisciplinas.setLayoutManager(new LinearLayoutManager(this));
        recyclerDisciplinas.setAdapter(adapter);
    }

    @OnClick(R.id.fab_adicionar_disciplina)
    public void abrirCadastroDisciplinas(View view) {
        startActivity(new Intent(this, CadastroDisciplinasActivity.class));

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerDisciplinas.setVisibility(View.VISIBLE);
            }
        }, 500);
    }


    private long getidUsuarioLogado() {
        SharedPreferences preferences = getSharedPreferences("estudosemdia.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return id;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_disciplinas, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.remove_all_disciplinas) {
            removerTodos();
        }

        return false;
    }

    private void removerTodos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Boletim")
                .setMessage("Deseja remover todas as disciplinas da lista?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<Disciplina> disciplinas = disciplinaBox.query().equal(Disciplina_.alunoId, idUsuarioLogado).build().find();
                        disciplinaBox.remove(disciplinas);
                        recyclerDisciplinas.setVisibility(View.GONE);
                        Snackbar.make(recyclerDisciplinas, "Todas as disciplinas foram removidas!", Snackbar.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NÃO", null)
                .create().show();

    }


    }

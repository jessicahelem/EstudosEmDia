package com.jessica.estudosemdias.activities;

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

import com.jessica.estudosemdias.Adapters.ListaEventosAdapter;
import com.jessica.estudosemdias.Model.Eventos;
import com.jessica.estudosemdias.Model.Eventos_;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class EventosActivity extends AppCompatActivity {

    @BindView(R.id.rv_lista_eventos) protected RecyclerView recyclerEventos;


    private Box<Eventos> eventosBox;
    private long idUsuarioLogado;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_eventos);
            ButterKnife.bind(this);

            eventosBox = ((App)getApplication()).getBoxStore().boxFor(Eventos.class);
            idUsuarioLogado = getidUsuarioLogado();
        }

        @Override
        protected void onResume() {
            super.onResume();

            List<Eventos> eventos = eventosBox.query().equal(Eventos_.alunoId, idUsuarioLogado).build().find();
            recyclerEventos.setLayoutManager(new LinearLayoutManager(this));
            ListaEventosAdapter adapter = new ListaEventosAdapter(this, eventos, eventosBox);
            recyclerEventos.setAdapter(adapter);
        }

        @OnClick(R.id.fab_adicionar_agendamento)
        public void abrirCadastroAgendamentos(View view) {
            startActivity(new Intent(this, CadastroEventos.class));

            view.postDelayed(() -> recyclerEventos.setVisibility(View.VISIBLE), 500);
        }

        private long getidUsuarioLogado() {
            SharedPreferences preferences = getSharedPreferences("estudosemdia.file", MODE_PRIVATE);
            long id = preferences.getLong("alunoId", -1);

            return id;
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.action_bar_menu_agendamentos, menu);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == R.id.remove_all_agendamentos) {
                removerTodos();
            }

            return false;
        }

        private void removerTodos() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Estudos em Dia")
                    .setMessage("Deseja remover todos os agendamentos da lista?")
                    .setPositiveButton("SIM", (dialogInterface, i) -> {
                        List<Eventos> eventos = eventosBox.query().equal(Eventos_.alunoId, idUsuarioLogado).build().find();
                        eventosBox.remove(eventos);
                        recyclerEventos.setVisibility(View.GONE);
                        Snackbar.make(recyclerEventos, "Todos os eventos foram removidos!", Snackbar.LENGTH_LONG).show();
                    })
                    .setNegativeButton("NÃO", null)
                    .create().show();
        }
    }


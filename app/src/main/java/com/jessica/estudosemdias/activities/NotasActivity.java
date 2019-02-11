package com.jessica.estudosemdias.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jessica.estudosemdias.Adapters.ListaNotasAdapter;
import com.jessica.estudosemdias.Model.Nota;
import com.jessica.estudosemdias.Model.Nota_;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;


public class NotasActivity extends AppCompatActivity {
        @BindView(R.id.rv_lista_notas) protected RecyclerView recyclerNotas;

        private Box<Nota> notaBox;
        private long disciplinaId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notas);
            ButterKnife.bind(this);

            disciplinaId = getIntent().getLongExtra("disciplinaId", -1);
            notaBox = ((App)getApplication()).getBoxStore().boxFor(Nota.class);
        }

        @Override
        protected void onResume() {
            super.onResume();

            List<Nota> notas = notaBox.query().equal(Nota_.disciplinaId, disciplinaId).build().find();
            recyclerNotas.setLayoutManager(new LinearLayoutManager(this));
            ListaNotasAdapter adapter = new ListaNotasAdapter(this, notas, notaBox);
            recyclerNotas.setAdapter(adapter);

            if(!notas.isEmpty()) {
                getSupportActionBar().setTitle(notas.get(0).getDisciplina().getTarget().getNome());
            }
        }
    }

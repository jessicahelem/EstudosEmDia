package com.jessica.estudosemdias.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jessica.estudosemdias.Model.Eventos;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;

public class VisualizarAnotacoes extends AppCompatActivity {

    @BindView(R.id.txt_ver_anotacao) protected TextView txtVerAnotacao;

    private Box<Eventos> eventosBox;
    private Eventos eventos;
    private long eventosId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_anotacoes);

        ButterKnife.bind(this);

        eventosBox = ((App)getApplication()).getBoxStore().boxFor(Eventos.class);
        eventosId = getIntent().getLongExtra("EventosId", -1);

        if(eventosId != -1) {
            eventos = eventosBox.get(eventosId);

            getSupportActionBar().setTitle(eventos.getTitulo());
            txtVerAnotacao.setText(eventos.getAnotacao());
        }
    }


    }


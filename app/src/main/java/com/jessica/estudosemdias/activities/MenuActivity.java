package com.jessica.estudosemdias.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jessica.estudosemdias.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.cardiview_disciplinas)
    public void AbrirDisciplina(View view) {
        startActivity(new Intent(this, DisciplinasActivity.class));


    }

    @OnClick(R.id.cardview_eventos)
    public void AbrirEventos(View view) {
        startActivity(new Intent(this, EventosActivity.class));


    }

    @OnClick(R.id.cardview_meus_dados)
    public void AbrirMeusDados(View view) {
        startActivity(new Intent(this, MeusDadosActivity.class));


    }

    @OnClick(R.id.cardview_sair)
    public void Sair(View view) {
        getSharedPreferences("estudosemdia.file", MODE_PRIVATE).edit().clear().commit();

        startActivity(new Intent(this, LoginActivity.class));
        finish();



    }


}

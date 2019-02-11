package com.jessica.estudosemdias.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.jessica.estudosemdias.Model.Usuario;

public class Login extends AppCompatActivity {
    public void logar(Usuario aluno) {
        SharedPreferences preferences = getSharedPreferences("estudosemdia.file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("alunoId", aluno.getId());

        editor.commit();

        startActivity(new Intent(this, MenuActivity.class));
    }




}

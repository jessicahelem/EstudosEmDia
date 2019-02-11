package com.jessica.estudosemdias.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import com.jessica.estudosemdias.Model.Usuario_;
import com.jessica.estudosemdias.Model.Usuario;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class LoginActivity extends Login {


    @BindView(R.id.usuario)  protected EditText editLoginUsuario;
    @BindView(R.id.senha) protected EditText editLoginSenha;

    private Box<Usuario> alunoBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        alunoBox = ((App)getApplication()).getBoxStore().boxFor(Usuario.class);
    }

    @OnClick(R.id.login_button)
    public  void entrar(View view){

        String nome = editLoginUsuario.getText().toString();
        String senha = editLoginSenha.getText().toString();

        List<Usuario> alunos =  alunoBox.query()
                .equal(Usuario_.nome,nome)
                .equal(Usuario_.senha, senha)
                .build().find();

        if(alunos.size() > 0) {
            logar(alunos.get(0));
            finish();
        }

        else {
            editLoginUsuario.getText().clear();
            editLoginSenha.getText().clear();

            Snackbar.make(view, "Usuário e/ou senha incorretos!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.txt_cadastrar)
    public void cadastrar(View view) {
        startActivity(new Intent(this, CadastroAlunoActivity.class));
    }





    }


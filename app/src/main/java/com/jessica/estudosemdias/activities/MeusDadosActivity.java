package com.jessica.estudosemdias.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.jessica.estudosemdias.Model.Usuario;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;

public class MeusDadosActivity extends AppCompatActivity {

    @BindView(R.id.txt_ver_nome) protected TextView txtVerNome;
    @BindView(R.id.txt_ver_instituicao) protected TextView txtVerInstituicao;
    @BindView(R.id.txt_ver_media_instituicao) protected TextView txtVerMediaMediaInstituicao;
    @BindView(R.id.txt_ver_media_pessoal) protected TextView txtVerMediaPessoal;
    @BindView(R.id.txt_ver_qtd_provas) protected TextView txtVerQtdProvas;

    private Box<Usuario> alunoBox;
    private Usuario aluno;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        alunoBox = ((App)getApplication()).getBoxStore().boxFor(Usuario.class);
        aluno = getAlunoLogado();

        txtVerNome.setText(aluno.getNome());
        txtVerInstituicao.setText(aluno.getInstitucao());
        txtVerMediaMediaInstituicao.setText(String.valueOf(aluno.getMediaInstitucional()));
        txtVerMediaPessoal.setText(String.valueOf(aluno.getMediaPessoal()));
        txtVerQtdProvas.setText(String.valueOf(aluno.getQtdProvas()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu_aluno, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.editar_aluno) {
            editar();
        }

        return false;
    }

    private void editar() {
        Intent intent = new Intent(this, CadastroAlunoActivity.class);
        intent.putExtra("alunoId", getAlunoLogado().getId());

        startActivity(intent);
    }

    private Usuario getAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("estudosemdia.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);
        return alunoBox.get(id);
    }
    
}

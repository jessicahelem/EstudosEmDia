package com.jessica.estudosemdias.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.jessica.estudosemdias.Model.Disciplina;
import com.jessica.estudosemdias.Model.Usuario;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class CadastroDisciplinasActivity extends AppCompatActivity {
    @BindView(R.id.edit_nome_disciplina) protected EditText editNomeDisciplina;
    @BindView(R.id.edit_professor) protected EditText editProfessor;
    @BindView(R.id.switch_disciplina_extra) protected Switch switchDisciplinaExtra;

    private Box<Disciplina> disciplinaBox;
    private Box<Usuario> alunoBox;
    private Usuario alunoLogado;
    private Disciplina disciplina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_disciplinas);
        ButterKnife.bind(this);

        disciplinaBox = ((App)getApplication()).getBoxStore().boxFor(Disciplina.class);
        alunoBox = ((App)getApplication()).getBoxStore().boxFor(Usuario.class);
        alunoLogado = getAlunoLogado();

        long disciplinaId = getIntent().getLongExtra("disciplinaId", -1);

        if(disciplinaId != -1) {
            disciplina = disciplinaBox.get(disciplinaId);

            editNomeDisciplina.setText(disciplina.getNome());
            editProfessor.setText(disciplina.getProfessor());
            switchDisciplinaExtra.setChecked(disciplina.isDisciplina_extra());
        }

        else {
            disciplina = new Disciplina();
        }
    }

    @OnClick(R.id.btn_salvar_disciplina)
    public void salvarDisciplina(View view) {
        String nomeDisciplina = editNomeDisciplina.getText().toString();
        String professor = editProfessor.getText().toString();
        boolean ehDisciplinaExtra = switchDisciplinaExtra.isChecked();

        if(nomeDisciplina.trim().isEmpty()) {
            editNomeDisciplina.setError("O campo não pode estar vazio!");
        }

        else if(professor.trim().isEmpty()) {
            editProfessor.setError("O campo não pode estar vazio!");
        }

        else {
            disciplina.setNome(nomeDisciplina);
            disciplina.setProfessor(professor);
            disciplina.setDisciplina_extra(ehDisciplinaExtra);
            disciplina.getAluno().setTarget(alunoLogado);

            disciplinaBox.put(disciplina);
            finish();
        }
    }

    private Usuario getAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("estudosemdia.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);
        return alunoBox.get(id);
    }
}
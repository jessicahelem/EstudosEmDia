package com.jessica.estudosemdias.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jessica.estudosemdias.Model.Usuario;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class CadastroAlunoActivity extends Login {

    @BindView(R.id.nome) protected EditText editNomeAluno;
    @BindView(R.id.senha) protected EditText editSenha;
    @BindView(R.id.instituicao) protected EditText editInstituicao;
    @BindView(R.id.media_institucional) protected EditText editMediaInstitucional;
    @BindView(R.id.media_pessoal) protected EditText editMediaPessoal;
    @BindView(R.id.cadastrar_button) protected Button btnSalvarAluno;

    private Box<Usuario> alunoBox;
    private Usuario aluno;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);
        ButterKnife.bind(this);

        alunoBox = ((App)getApplication()).getBoxStore().boxFor(Usuario.class);
        long id = getIntent().getLongExtra("alunoId", -1);

        if(id != -1) {
            aluno = alunoBox.get(id);

            editNomeAluno.setText(aluno.getNome());
            editSenha.setText(aluno.getSenha());
            editInstituicao.setText(aluno.getInstitucao());

            editMediaInstitucional.setText(String.valueOf(aluno.getMediaInstitucional()));
            editMediaPessoal.setText(String.valueOf(aluno.getMediaPessoal()));
        }

        else {
            aluno = new Usuario();
        }
    }
    @OnClick(R.id.cadastrar_button)
    public  void SalvarAluno(View view){
        try {
            String mediaInstitucional = editMediaInstitucional.getText().toString();
            String mediaPessoal =  editMediaPessoal.getText().toString();
            String nomeAluno = editNomeAluno.getText().toString();
            String senha = editSenha.getText().toString();
            String instituicao = editInstituicao.getText().toString();

            aluno.setNome(nomeAluno);
            aluno.setSenha(senha);
            aluno.setInstitucao(instituicao);
            aluno.setMediaInstitucional(Double.valueOf(mediaInstitucional));
            aluno.setMediaPessoal(Double.valueOf(mediaPessoal));

            if(nomeAluno.trim().isEmpty()) {
                editNomeAluno.setError("O campo não pode estar vazio!");
            }



            else if(senha.trim().isEmpty()) {
                editSenha.setError("O campo não pode estar vazio!");
            }

            else if(instituicao.trim().isEmpty()) {
                editInstituicao.setError("O campo não pode estar vazio!");
            }

            else if(mediaInstitucional.trim().isEmpty()) {
                editMediaInstitucional.setError("O campo não pode estar vazio!");
            }

            else if(mediaPessoal.trim().isEmpty() || mediaInstitucional.trim().isEmpty()) {
                editMediaPessoal.setError("O campo não pode estar vazio!");
            }

            else if(Double.valueOf(mediaPessoal) < Double.valueOf(mediaInstitucional)) {
                editMediaPessoal.setError("Média pessoal não pode ser menor que a média Institucional!");
            }

            else {
                alunoBox.put(aluno);

                finish();

                logar(aluno);
            }
        }

        catch (NumberFormatException e) {
            Snackbar.make(view, "Média pessoal, média institucional e quantidade de provas não podem estar vazios!", Snackbar.LENGTH_LONG).show();
        }
    }

        }




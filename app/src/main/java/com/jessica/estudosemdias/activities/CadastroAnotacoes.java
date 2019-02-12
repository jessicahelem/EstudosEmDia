package com.jessica.estudosemdias.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jessica.estudosemdias.Model.Eventos;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class CadastroAnotacoes extends AppCompatActivity {

    @BindView(R.id.edit_anotacao) protected EditText editAnotacao;

    private Box<Eventos> eventosBox;
    private Eventos eventos;
    private long idAlunoLogado;
    private long agendamentoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anotacoes);

        ButterKnife.bind(this);

        eventosBox = ((App)getApplication()).getBoxStore().boxFor(Eventos.class);
       idAlunoLogado= getIdAlunoLogado();
        agendamentoId = getIntent().getLongExtra("agendamentoId", -1);

        if(agendamentoId != -1) {
            eventos = eventosBox.get(agendamentoId);
            editAnotacao.setText(eventos.getAnotacao());
        }
    }

    @OnClick(R.id.btn_salvar_anotacao)
    public void salvarAnotacao(View view) {
        String anotacao = editAnotacao.getText().toString();
        eventos.setAnotacao(anotacao);
        eventos.getAluno().setTargetId(idAlunoLogado);
        eventosBox.put(eventos);

        finish();
    }

    @OnClick(R.id.btn_cancelar_anotacao)
    public void cancelar(View view) {
        finish();
    }

    private long getIdAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("estudosemdia.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return id;
    }
        
    }


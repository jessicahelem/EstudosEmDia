package com.jessica.estudosemdias.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jessica.estudosemdias.Model.Disciplina;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class CadastroNotaFinalActivity extends AppCompatActivity {

    @BindView(R.id.edit_nota_prova_final) protected EditText editNotaProvaFinal;

    private Box<Disciplina> disciplinaBox;
    private Disciplina disciplina;
    private long disciplinaId;
    private long  idUsuarioLogado;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nota_final);
        ButterKnife.bind(this);

        disciplinaBox = ((App)getApplication()).getBoxStore().boxFor(Disciplina.class);
        idUsuarioLogado = getIdUsuarioLogado();
        disciplinaId = getIntent().getLongExtra("disciplinaId", -1);

        if(disciplinaId != -1) {
            disciplina = disciplinaBox.get(disciplinaId);
            editNotaProvaFinal.setText(String.valueOf(disciplina.getProvaFinal()));
        }
    }

    @OnClick(R.id.btn_salvar_prova_final)
    public void salvarProvaFinal(View view) {
        String notaProvaFinal = editNotaProvaFinal.getText().toString();

        if(editNotaProvaFinal.getText().length() == 0) {
            editNotaProvaFinal.setError("O campo não pode estar vazio!");
        }

        else if(Double.valueOf(notaProvaFinal) < 0 || Double.valueOf(notaProvaFinal) > 10) {
            editNotaProvaFinal.setError("Somente valores de 0 a 10 são permitidos!");
        }

        else {try {
            disciplina.setProvaFinal(Double.valueOf(notaProvaFinal));
            disciplina.getAluno().setTargetId(idUsuarioLogado);
            disciplinaBox.put(disciplina);

            finish();
        }catch (Exception e){
            Toast.makeText(this, "aqui", Toast.LENGTH_SHORT).show();
        }
        }
    }

    @OnClick(R.id.btn_cancelar_prova_final)
    public void cancelar(View view) {
        finish();
    }

    private long getIdUsuarioLogado() {
        SharedPreferences preferences = getSharedPreferences("estudosemdia.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return id;
    }    
        
        
    
}

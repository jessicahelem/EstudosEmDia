package com.jessica.estudosemdias.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jessica.estudosemdias.Model.Eventos;
import com.jessica.estudosemdias.Model.Usuario;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.dal.App;
import com.jessica.estudosemdias.fragment.TimePickerFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

public class CadastroEventos extends AppCompatActivity {

    @BindView(R.id.calendario)
    protected MaterialCalendarView materialCalendarView;
    @BindView(R.id.edit_titulo)
    protected EditText editTitulo;
    @BindView(R.id.edit_hora)
    protected EditText editHora;

    private Box<Eventos> agendamentoBox;
    private Eventos agendamento;
    private long agendamentoId;
    private long idAlunoLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_eventos);
        ButterKnife.bind(this);

        agendamentoBox = ((App) getApplication()).getBoxStore().boxFor(Eventos.class);
        agendamentoId = getIntent().getLongExtra("agendamentoId", -1);
        idAlunoLogado = getIdAlunoLogado();

        if (agendamentoId != -1) {
            agendamento = agendamentoBox.get(agendamentoId);

            materialCalendarView.setSelectedDate(agendamento.getData());
            editTitulo.setText(agendamento.getTitulo());
            editHora.setText(agendamento.getHora());
        } else {
            agendamento = new Eventos();
        }

        configurarCalendario();
    }

    public void configurarCalendario() {
        Calendar calendar = Calendar.getInstance();

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(calendar.get(Calendar.YEAR), 0, 1))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setSelectedDate(new Date());
    }

    @OnClick(R.id.edit_hora)
    public void abrirTimePicker(View view) {
        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @OnClick(R.id.btn_salvar_evento)
    public void salvarAgendamento(View view) {
        Date data = materialCalendarView.getSelectedDate().getDate();
        String hora = editHora.getText().toString();
        String titulo = editTitulo.getText().toString();

        if (titulo.trim().isEmpty()) {
            editTitulo.setText("Sem Título");
        } else if (hora.trim().isEmpty()) {
            editHora.setError("O campo não pode estar vazio");
        } else if (materialCalendarView.getSelectedDate().getCalendar().get(Calendar.DATE) < Calendar.getInstance().get(Calendar.DATE)) {
            Snackbar.make(view, "Você não pode selecionar uma data anterior a data de hoje!", Snackbar.LENGTH_LONG).show();
        } else {
            agendamento.setData(data);
            agendamento.setHora(hora);
            agendamento.setTitulo(titulo);
            agendamento.getAluno().setTargetId(idAlunoLogado);

            agendamentoBox.put(agendamento);

            finish();
        }
    }

    private long getIdAlunoLogado() {
        SharedPreferences preferences = getSharedPreferences("estudosemdia.file", MODE_PRIVATE);
        long id = preferences.getLong("alunoId", -1);

        return id;
    }

}
package com.jessica.estudosemdias.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jessica.estudosemdias.Model.Eventos;
import com.jessica.estudosemdias.R;
import com.jessica.estudosemdias.activities.VisualizarAnotacoes;
import com.jessica.estudosemdias.activities.CadastroEventos;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;

public class ListaEventosAdapter extends RecyclerView.Adapter<ListaEventosAdapter.ViewHolder> {
    private Context context;
    private Eventos evento;
    private List<Eventos> eventos;
    private Box<Eventos> eventosBox;

    public ListaEventosAdapter(Context context, List<Eventos> eventos, Box<Eventos> eventosBox) {
        this.context = context;
        this.eventos = eventos;
        this.eventosBox = eventosBox;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_data) TextView txtData;
        @BindView(R.id.txt_hora) TextView txtHora;
        @BindView(R.id.txt_titulo) TextView txtTitulo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_eventos, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Eventos evento = this.eventos.get(position);

        holder.txtTitulo.setText(evento.getTitulo());
        holder.txtData.setText(" " + new SimpleDateFormat("dd/MM/yyyy").format(evento.getData()));
        holder.txtHora.setText(" " + evento.getHora());

        configurarClickLongo(holder.itemView, evento, position);
        configurarClickCurto(holder.itemView, evento, position);
    }

    @Override
    public int getItemCount() {
        return this.eventos.size();
    }

    public void configurarClickLongo(final View itemView, final Eventos evento, final int position) {
        itemView.setOnLongClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_agendamentos, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.adicionar_anotacoes) {
                    adicionarAnotacao(itemView, evento, position);
                }

                else if(item.getItemId() == R.id.editar_agendamento) {
                    editar(itemView, evento, position);
                }

                else if(item.getItemId() == R.id.remover_agendamento) {
                    remover(itemView, evento, position);
                }

                return false;
            });

            popupMenu.show();

            return true;
        });
    }

    public void configurarClickCurto(final View itemView, final Eventos evento, final int position) {
        itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, VisualizarAnotacoes.class).putExtra("eventosId", evento.getId()));
        });
    }

    public void adicionarAnotacao(View view, Eventos evento, int position) {
        Intent intent = new Intent(context, CadastroEventos.class);
        intent.putExtra("eventosId", evento.getId());
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    public void editar(View view, Eventos evento, int position) {
        Intent intent = new Intent(context, CadastroEventos.class);

        intent.putExtra("eventosId", evento.getId());
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    public void remover(View view, Eventos evento, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("EstudosEmDia");
        builder.setMessage("Deseja remover o eventos da lista?");

        builder.setPositiveButton("SIM", (dialog, which) -> {
            this.eventos.remove(evento);
            this.eventosBox.remove(evento);

            notifyItemRemoved(position);
            notifyItemChanged(position);
            Snackbar.make(view, "Evento removido!", Snackbar.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("NÃO", null);
        builder.create().show();
    }
}
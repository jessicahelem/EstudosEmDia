package com.jessica.estudosemdias.Model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity

public class Eventos {
    @Id private long id;
    private String titulo;
    private Date data;
    private String hora;
    private String anotacao;
    private ToOne<Usuario> aluno;


    public Eventos() {}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAluno(ToOne<Usuario> aluno) {
        this.aluno = aluno;
    }

    public ToOne<Usuario> getAluno() {
        return aluno;
    }
}
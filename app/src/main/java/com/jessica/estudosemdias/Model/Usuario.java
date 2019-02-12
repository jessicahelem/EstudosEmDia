package com.jessica.estudosemdias.Model;


import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Usuario {
    @Id  private long id;
    private String nome;
    private String senha;
    private String institucao;
    private double mediaInstitucional;
    private double mediaPessoal;
    final private static int qtdProvas = 4;
    @Backlink private ToMany<Disciplina> disciplinas;
    @Backlink private ToMany<Eventos> eventos;


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setInstitucao(String institucao) {
        this.institucao = institucao;
    }

    public String getInstitucao() {
        return institucao;
    }

    public void setMediaInstitucional(double mediaInstitucional) {
        this.mediaInstitucional = mediaInstitucional;
    }

    public double getMediaInstitucional() {
        return mediaInstitucional;
    }

    public void setMediaPessoal(double mediaPessoal) {
        this.mediaPessoal = mediaPessoal;
    }

    public double getMediaPessoal() {
        return mediaPessoal;
    }

    public int getQtdProvas() {
        return qtdProvas;
    }

    public void setDisciplinas(ToMany<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public ToMany<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setEventos(ToMany<Eventos> eventos) {
        this.eventos = eventos;
    }

    public ToMany<Eventos> getEventos() {
        return eventos;
    }








}

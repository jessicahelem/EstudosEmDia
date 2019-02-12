package com.jessica.estudosemdias.Model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Disciplina {
    @Id private long id;
    private String nome;
    private boolean disciplina_extra;
    private String professor;
    private double media;
    private double provaFinal;
    private ToOne<Usuario> aluno;
    private ToMany<Nota> notas;

    public Disciplina() {
    }

    public Disciplina(String nome, String professor){this.nome = nome;
        this.professor = professor;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDisciplina_extra() {
        return disciplina_extra;
    }


    public void setDisciplina_extra(boolean disciplina_extra) {
        this.disciplina_extra = disciplina_extra;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getProvaFinal() {
        return provaFinal;
    }

    public void setProvaFinal(double provaFinal) {
        this.provaFinal = provaFinal;
    }

    public ToOne<Usuario> getAluno() {
        return aluno;
    }

    public void setAluno(ToOne<Usuario> aluno) {
        this.aluno = aluno;
    }

    public ToMany<Nota> getNotas() {
        return notas;
    }

    public void setNotas(ToMany<Nota> notas) {
        this.notas = notas;
    }



    public double getMedia() {
        int totalDeProvas = getAluno().getTarget().getQtdProvas();
        double soma = 0;
        double mediaInstitucional = getAluno().getTarget().getMediaInstitucional();

        for(int i = 0; i < getNotas().size(); i++) {
            soma += getNotas().get(i).getNotaBimestral();
        }

        this.media = soma / totalDeProvas;

        if(provaFinal >= mediaInstitucional) {
            return provaFinal;
        }

        return media;
    }

    public String informarSituacao() {
        double mediaInstitucional = getAluno().getTarget().getMediaInstitucional();
        double mediaPessoal = getAluno().getTarget().getMediaPessoal();

        if(estaDeProvaFinal()) {
            if(getMedia() >= mediaInstitucional && getMedia() > mediaPessoal) {
                return "Parabéns! Você foi aprovado pela prova final e está acima da sua média pessoal.";
            }

            else if(getMedia() >= mediaInstitucional && getMedia() == mediaPessoal) {
                return "Parabéns! Você foi aprovado pela prova final e atingiu sua média pessoal.";
            }

            else if(getMedia() >= mediaInstitucional && getMedia() < mediaPessoal) {
                return "Parabéns! Você foi aprovado pela prova final, mas ainda está abaixo da sua média pessoal.";
            }
        }

        else {
            if(getMedia() >= mediaInstitucional && getMedia() > mediaPessoal) {
                return "Parabéns! Você foi aprovado e está acima da sua média pessoal.";
            }

            else if(getMedia() >= mediaInstitucional && getMedia() == mediaPessoal) {
                return "Parabéns! Você foi aprovado pela prova final e atingiu sua média pessoal.";
            }

            else if(getMedia() >= mediaInstitucional && getMedia() < mediaPessoal) {
                return "Parabéns! Você foi aprovado, mas ainda está abaixo da sua média pessoal.";
            }
        }

        return "Infelizmente, você reprovou.";
    }

    public boolean estaDeProvaFinal() {
        double mediaInstitucional = getAluno().getTarget().getMediaInstitucional();

        if(getMedia() < mediaInstitucional && provaFinal == 0) {
            return true;
        }

        return false;
    }

}

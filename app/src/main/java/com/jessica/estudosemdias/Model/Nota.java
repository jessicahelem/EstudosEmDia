package com.jessica.estudosemdias.Model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Nota {

    @Id
    private long id;
    private double notaBimestral;
    ToOne<Disciplina> disciplina;

    public Nota() {
    }

    public void setId(long id){this.id=id;}

    public long getId(){return id;}


    public double getNotaBimestral() {
        return notaBimestral;
    }

    public void setNotaBimestral(double notaBimestral) {
        this.notaBimestral = notaBimestral;
    }

    public ToOne<Disciplina> getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(ToOne<Disciplina> disciplina) {
        this.disciplina = disciplina;
    }





}


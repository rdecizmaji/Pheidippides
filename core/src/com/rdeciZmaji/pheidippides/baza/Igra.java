package com.rdeciZmaji.pheidippides.baza;

/**
 * Created by Spela on 23.1.2017.
 */

public class Igra {
    public long id;
    public int tocke;
    public int id_level_fk;
    public int id_igralec_fk;
    public int id_lik_fk;

    public Igra(long id, int tocke, int id_lik_fk, int id_igralec_fk, int id_level_fk){
        this.id=id;
        this.tocke=tocke;
        this.id_level_fk=id_level_fk;
        this.id_igralec_fk=id_igralec_fk;
        this.id_lik_fk=id_lik_fk;
    }
    public long getId(){
        return id;
    }
    public int getTocke(){
        return tocke;
    }
    public int getLevel(){
        return id_level_fk;
    }
    public int getIgralec(){
        return id_igralec_fk;
    }
    public int getLik(){
        return id_lik_fk;
    }
}

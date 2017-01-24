package com.rdeciZmaji.pheidippides.baza;


public class Lik {
    private long id;
    private String naziv;
    private String skok;

    public Lik(long id, String naziv, String skok){
        this.id=id;
        this.naziv=naziv;
        this.skok=skok;
    }
    public long getId(){
        return id;
    }
    public String getNaziv(){
        return naziv;
    }
    public String getSkok(){
        return skok;
    }
}

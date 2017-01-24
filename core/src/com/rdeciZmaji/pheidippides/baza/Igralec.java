package com.rdeciZmaji.pheidippides.baza;

public class Igralec {
    private long id;
    private String ime;
    private String last_login;

    public Igralec(long id, String ime, String last_login){
        this.id=id;
        this.ime=ime;
        this.last_login=last_login;
    }
    public long getId(){
        return id;
    }
    public String getIme(){
        return ime;
    }
    public String getLogin(){
        return last_login;
    }
}

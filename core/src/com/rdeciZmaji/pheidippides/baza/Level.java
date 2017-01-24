package com.rdeciZmaji.pheidippides.baza;


public class Level {
    private long id;
    private String naziv_level;
    private String ovire;
    private String gravitacija;

    public Level(long id, String naziv_level, String ovire, String  gravitacija){
        this.id=id;
        this.naziv_level=naziv_level;
        this.ovire=ovire;
        this.gravitacija=gravitacija;
    }

    public long getId(){
        return id;
    }
    public String getNaziv(){
        return naziv_level;
    }
    public String getOvire(){
        return ovire;
    }
    public String getGravitacija(){
        return gravitacija;
    }
}

package com.rdeciZmaji.pheidippides.baza;

import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.SQLiteGdxException;

import java.util.ArrayList;
import java.util.List;

public class DAO {
    private Database database;
    private MySQL dbHelper;
    private String[] vsiStolpciIgra = {
            "_ID",
            MySQL.COLUMN_TOCKE,
            MySQL.COLUMN_ID_LEVEL_FK,
            MySQL.COLUMN_ID_IGRALEC_FK,
            MySQL.COLUMN_ID_LIK_FK
    };
    private String[] vsiStolpciIgralec = {
            "ID",
            MySQL.COLUMN_IME,
            MySQL.COLUMN_LAST_LOGIN
    };
    private String[] vsiStolpciLik = {
            "_ID",
            MySQL.COLUMN_NAZIV,
            MySQL.COLUMN_SKOK
    };

    private String[] vsiStolpciLevel = {
           "ID",
            MySQL.COLUMN_NAZIV_LEVEL,
            MySQL.COLUMN_OVIRE,
            MySQL.COLUMN_GRAVITACIJA,
    };

    public DAO() {
        dbHelper = new MySQL();
    }

    public void open() {
        database = dbHelper.open();
        try {
            database.openOrCreateDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            database.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void createIgra(int tocke, int id_level_fk, int id_igralec_fk, int id_lik_fk) {

       try {
            database.execSQL("INSERT INTO igra (tocke, id_level_fk, id_igralec_fk, id_lik_fk) VALUES ("+tocke+", "+id_level_fk+", "+id_igralec_fk+", "+id_lik_fk+")");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void createIgralec(String ime, String last_login) {

        try {
            database.execSQL("INSERT INTO igralec (ime, last_login) VALUES ('"+ime+"','"+last_login+"')");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void createLik(String naziv, String skok) {

        try {
            database.execSQL("INSERT INTO lik (naziv, skok) VALUES ('"+naziv+"','"+skok+"')");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void createLevel(String naziv_level, String ovire, String gravitacija) {
        try {
            database.execSQL("INSERT INTO level (naziv_level, ovire, gravitacija) VALUES ('"+naziv_level+"','"+ovire+"','"+gravitacija+"')");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void izbrisiIgra(Igra igra) {
        long id = igra.getId();
        try {
            database.execSQL("DELETE FROM igra WHERE _ID="+id);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void izbrisiIgralec(Igralec igralec) {
        long id = igralec.getId();
        try {
            database.execSQL("DELETE FROM igralec WHERE ID="+id);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }
    public void izbrisiLik(Lik lik) {
        long id = lik.getId();
        try {
            database.execSQL("DELETE FROM lik WHERE _ID="+id);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }
    public void izbrisiLevel(Level level) {
        long id = level.getId();
        try {
            database.execSQL("DELETE FROM level WHERE ID="+id);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }


    public List<Igra> getIgre() {
        List<Igra> igra = new ArrayList<Igra>();
        DatabaseCursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM igra");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        while (cursor.next()) {
            Igra ig = cursorToIgra(cursor);
            igra.add(ig);

        }

        cursor.close();
        return igra;
    }

    public List<Igralec> getIgralec() {
        List<Igralec> igralci = new ArrayList<Igralec>();
        DatabaseCursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM igralec");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        while (cursor.next()) {
            Igralec ig = cursorToIgralec(cursor);
            igralci.add(ig);

        }

        cursor.close();
        return igralci;
    }

    public List<Lik> getLiki() {
        List<Lik> liki = new ArrayList<Lik>();
        DatabaseCursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM lik");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        while (cursor.next()) {
            Lik lk = cursorToLik(cursor);
            liki.add(lk);

        }

        cursor.close();
        return liki;
    }

    public  List<Level> getLevel() {
        List<Level> level = new ArrayList<Level>();
        DatabaseCursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM level");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        while (cursor.next()) {
            Level le = cursorToLevel(cursor);
            level.add(le);

        }

        cursor.close();
        return level;
    }

    private Igra cursorToIgra(DatabaseCursor cursor) {
        Igra i = new Igra(cursor.getLong(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4));
        return i;
    }
    private Igralec cursorToIgralec(DatabaseCursor cursor) {
        Igralec igr = new Igralec(cursor.getLong(0),cursor.getString(1),cursor.getString(2));
        return igr;
    }
    private Lik cursorToLik(DatabaseCursor cursor) {
        Lik l = new Lik(cursor.getLong(0),cursor.getString(1),cursor.getString(2));
        return l;
    }
    private Level cursorToLevel(DatabaseCursor cursor) {
        Level l = new Level(cursor.getLong(0),cursor.getString(1),cursor.getString(2), cursor.getString(3));
        return l;
    }
}

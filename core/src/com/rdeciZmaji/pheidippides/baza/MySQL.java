package com.rdeciZmaji.pheidippides.baza;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

public class MySQL{
    public static Database dbHandler;

        public static final String TABLE_IGRALEC = "igralec";
        public static final String COLUMN_IME = "ime";
        public static final String COLUMN_LAST_LOGIN = "last_login";

        public static final String TABLE_IGRA = "igra";
        public static final String COLUMN_TOCKE = "tocke";
        public static final String COLUMN_ID_LEVEL_FK = "id_level_fk";
        public static final String COLUMN_ID_IGRALEC_FK = "id_igralec_fk";
        public static final String COLUMN_ID_LIK_FK = "id_lik_fk";

        public static final String TABLE_LIK = "lik";
        public static final String COLUMN_NAZIV = "naziv";
        public static final String COLUMN_SKOK = "skok";

        public static final String TABLE_LEVEL = "level";
        public static final String COLUMN_NAZIV_LEVEL = "naziv_level";
        public static final String COLUMN_OVIRE = "ovire";
        public static final String COLUMN_GRAVITACIJA = "gravitacija";


    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES1 =
            "CREATE TABLE IF NOT EXISTS " + TABLE_IGRALEC + " (" +
                    "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_LAST_LOGIN + TEXT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE IF NOT EXISTS " + TABLE_IGRA + " (" +
                    "_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TOCKE + INTEGER_TYPE + COMMA_SEP +
                    COLUMN_ID_LEVEL_FK + INTEGER_TYPE + COMMA_SEP +
                    COLUMN_ID_IGRALEC_FK + INTEGER_TYPE + COMMA_SEP +
                    COLUMN_ID_LIK_FK + INTEGER_TYPE+ COMMA_SEP +
                    " FOREIGN KEY ("+ COLUMN_ID_LIK_FK+") REFERENCES "+ TABLE_LIK+"("+ "_ID"+")," +
                    " FOREIGN KEY ("+ COLUMN_ID_IGRALEC_FK+") REFERENCES "+ TABLE_IGRALEC+"("+ "ID"+"), "+
                    " FOREIGN KEY ("+ COLUMN_ID_LEVEL_FK+") REFERENCES "+ TABLE_LEVEL+"("+ "ID"+"))";

    private static final String SQL_CREATE_ENTRIES3 =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LIK + " (" +
                    "_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAZIV + TEXT_TYPE + COMMA_SEP +
                    COLUMN_SKOK + TEXT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIES4 =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LEVEL + " (" +
                    "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                   COLUMN_NAZIV_LEVEL + TEXT_TYPE + COMMA_SEP +
                    COLUMN_OVIRE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_GRAVITACIJA + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES1 = "DROP TABLE IF EXISTS " + TABLE_IGRALEC;
    private static final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + TABLE_IGRA;
    private static final String SQL_DELETE_ENTRIES3 = "DROP TABLE IF EXISTS " + TABLE_LIK;
    private static final String SQL_DELETE_ENTRIES4 = "DROP TABLE IF EXISTS " + TABLE_LEVEL;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pheidippides.db";

    public MySQL() {
        Gdx.app.log("DatabaseTest", "creation started");
        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME, DATABASE_VERSION, SQL_CREATE_ENTRIES1, null);
        dbHandler.setupDatabase();
        onCreate(dbHandler);
    }
    public Database open(){
        return dbHandler;
    }

    public void onCreate(Database dbHandler) {
        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(SQL_CREATE_ENTRIES1);
            dbHandler.execSQL(SQL_CREATE_ENTRIES3);
            dbHandler.execSQL(SQL_CREATE_ENTRIES4);
            dbHandler.execSQL(SQL_CREATE_ENTRIES2);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void onUpgrade() {
        try {
            dbHandler.execSQL(SQL_DELETE_ENTRIES2);
            dbHandler.execSQL(SQL_DELETE_ENTRIES1);
            dbHandler.execSQL(SQL_DELETE_ENTRIES3);
            dbHandler.execSQL(SQL_DELETE_ENTRIES4);
            onCreate(dbHandler);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }


}
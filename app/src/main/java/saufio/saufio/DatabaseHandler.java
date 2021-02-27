package saufio.saufio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 2;
        private static final String DATABASE_NAME = "aufgabedb";
        private static final String TABLE_AUFGABE = "aufgabetb";
        private static final String KEY_ID = "id";
        private static final String KEY_AUFGABETXT = "aufgabetxt";
        private static final String KEY_KATEGORIE = "kategorie";
        private static final String KEY_AENDERUNG = "aenderung";


        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Erstelle Tabelle
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_AUFGABE + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AUFGABETXT + " TEXT," + KEY_AENDERUNG + " TEXT," + KEY_KATEGORIE + " TEXT)";
            db.execSQL(CREATE_CONTACTS_TABLE);
            Log.i("Database", "Create Table");
        }

        // Upgrade Database database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUFGABE);
            Log.i("Database", "DROP Table");
            // Create tables again
            onCreate(db);
        }

        // aufgabe hinzufügen
        void addAufgabe(Aufgabe aufgabe) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_AUFGABETXT, aufgabe.getAufgabe());
            values.put(KEY_AENDERUNG, aufgabe.getAenderung());
            values.put(KEY_KATEGORIE, aufgabe.getKategorie());
            Log.i("TEST", values.toString());
            // Inserting Row
            try {
                db.insert(TABLE_AUFGABE, null, values);
            } catch (Exception e) {
                Log.i("Database1", String.valueOf(e));
            }

            //2nd argument is String containing nullColumnHack
            db.close(); // Closing database connection
            Log.i("Database", "Aufgabe hinzugefügt: " + aufgabe.getAufgabe());
        }

        // einzelne Aufgabe bekommen
        Aufgabe getAufgabe(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_AUFGABE, new String[]{KEY_ID,
                            KEY_AUFGABETXT, KEY_AENDERUNG, KEY_KATEGORIE}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }
            Aufgabe aufgabe = new Aufgabe(1, null, null, null);
            aufgabe.setId(Integer.parseInt(cursor.getString(0)));
            aufgabe.setAufgabe(cursor.getString(1));
            aufgabe.setAenderung(cursor.getString(2));
            aufgabe.setKategorie(cursor.getString(3));
            Log.i("Database", "Aufgabe bekommmen: " + aufgabe.getAufgabe());
            cursor.close();
            db.close();
            return aufgabe;
        }

        public Aufgabe sucheUeberTxt(String suche) {
            Aufgabe aufgabe = new Aufgabe();
            String selectQuery = "SELECT  * FROM " + TABLE_AUFGABE + " where " + KEY_AUFGABETXT + "='" + suche + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                aufgabe.setId(Integer.parseInt(cursor.getString(0)));
                aufgabe.setAufgabe(cursor.getString(1));
                aufgabe.setAenderung(cursor.getString(2));
                aufgabe.setKategorie(cursor.getString(3));
            }
            Log.i("Database", aufgabe.getAufgabe() + "   " + aufgabe.id);
            return aufgabe;
        }

        public List<String> getAllAufgabeString() {
            List<String> aufgabeList = new ArrayList<String>();
            String selectQuery = "SELECT  * FROM " + TABLE_AUFGABE;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Aufgabe aufgabe = new Aufgabe();
                    aufgabe.setId(Integer.parseInt(cursor.getString(0)));
                    aufgabe.setAufgabe(cursor.getString(1));
                    aufgabe.setAenderung(cursor.getString(2));
                    aufgabe.setKategorie(cursor.getString(3));
                    aufgabeList.add(aufgabe.getAufgabe());
                } while (cursor.moveToNext());
            }
            // return list
            Log.i("Database", "Aufgabe Liste: " + aufgabeList);
            return aufgabeList;
        }

        // alle aufgaben in List
        public List<Aufgabe> getAllAufgabe() {
            List<Aufgabe> aufgabeList = new ArrayList<Aufgabe>();
            String selectQuery = "SELECT  * FROM " + TABLE_AUFGABE;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Aufgabe aufgabe = new Aufgabe();
                    aufgabe.setId(Integer.parseInt(cursor.getString(0)));
                    aufgabe.setAufgabe(cursor.getString(1));
                    aufgabeList.add(aufgabe);
                } while (cursor.moveToNext());
            }
            // return list
            Log.i("Database", "Aufgabe Liste: " + aufgabeList);
            return aufgabeList;
        }

        // einzelne Aufgabe upgrade
        public int updateAufgabe(Aufgabe aufgabe) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_AUFGABETXT, aufgabe.getAufgabe());
            // updating row
            Log.i("Database", "Update Aufgabe:+ " + aufgabe.getAufgabe());
            return db.update(TABLE_AUFGABE, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(aufgabe.getId())});
        }

        // Lösche aufgabe
        public void deleteAufgabe(Aufgabe aufgabe) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_AUFGABE, KEY_ID + " = ?",
                    new String[]{String.valueOf(aufgabe.getId())});
            db.close();
            Log.i("Database", "Aufgabe gelöscht " + aufgabe.getId() + " " + aufgabe.getAufgabe());
        }

        public void deleteTabeleLeeren() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_AUFGABE, null, null);
            db.close();
            Log.i("Database", "Tabelle geleert");
        }

        public int getLastID() {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT MAX(" + KEY_ID + ") FROM " + TABLE_AUFGABE, null);
            int maxid = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
            try {
                Log.i("Database", "Maxid: " + maxid);
                return maxid;
            } catch (Exception e) {
                Log.i("Database", String.valueOf(e));
                return 0;
            }
        }

        public int getFirstID() {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT min(" + KEY_ID + ") FROM " + TABLE_AUFGABE, null);
            int minid = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
            try {
                Log.i("Database", "Maxid: " + minid);
                return minid;
            } catch (Exception e) {
                Log.i("Database", String.valueOf(e));
                return 0;
            }
        }

        public List<String> getKategorien() {
            List<String> kategorieList = new ArrayList<String>();
            String selectQuery = "SELECT max(" + KEY_KATEGORIE + ")," + KEY_KATEGORIE + " FROM " + TABLE_AUFGABE + " group by " + KEY_KATEGORIE + " order by " + KEY_KATEGORIE;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    kategorieList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            Log.i("Database", "Aufgabe Liste: " + kategorieList);
            return kategorieList;
        }

        public List<String> aufgabeKategorie(String kategorien) {
            List<String> kategoriegefiltertList = new ArrayList<String>();
            String selectQuery = "Select " + KEY_AUFGABETXT + " FROM " + TABLE_AUFGABE + " where " + KEY_KATEGORIE + " in (" + kategorien + ") order by " + KEY_AUFGABETXT;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    kategoriegefiltertList.add(cursor.getString(0));
                    Log.i("Database", "Aufgabe gefiltert Liste: " + kategoriegefiltertList.get(0));
                } while (cursor.moveToNext());
            }
            // return list
            Log.i("Database", "Aufgabe gefiltert Liste: " + kategoriegefiltertList);
            return kategoriegefiltertList;
        }

    }

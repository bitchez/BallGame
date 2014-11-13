package ball.DataSources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ball.Models.Stunt;

/**
 * Created by jburkhart on 10/16/2014.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "data/data/com.game.ball.ballgame/databases/";
    private static String DB_NAME = "StuntsDB";
    private static String TABLE_STUNTS = "Stunts";
    private static String COLUMN_STUNT_ID = "StuntId";
    private static String COLUMN_STUNT_NAME = "StuntName";
    private String[] allColumns = { COLUMN_STUNT_ID, COLUMN_STUNT_NAME};
    private final Context context;
    private SQLiteDatabase db;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    // Creates a empty database on the system and rewrites it with your own database.
    public void create() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist){
            //do nothing - database already exist
            //copyDataBase();
        }
        else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try { copyDataBase(); } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database don't exist yet.
            e.printStackTrace();
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    // copy your assets db to the new system DB
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //Open the database
    public boolean open()
    {
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        return true;
    }

    @Override
    public synchronized void close()
    {
        if (db != null) {
            db.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<Stunt> getStunts() {

        List<Stunt> stunts = new ArrayList<Stunt>();

        try {
            String query  = "SELECT * FROM " + TABLE_STUNTS;
            SQLiteDatabase db = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    Stunt stunt = new Stunt();
                    stunt.stuntId = Integer.parseInt(cursor.getString(0));
                    stunt.stuntName = cursor.getString(1);
                    stunts.add(stunt);

                } while (cursor.moveToNext());
            }
        } catch(Exception e)  { }

        return (ArrayList<Stunt>) stunts;
    }

    public boolean updateStunt(Stunt selectedStunt) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_STUNT_NAME, selectedStunt.stuntName);
        return db.update(TABLE_STUNTS, args, COLUMN_STUNT_ID + "=" + selectedStunt.stuntId, null) > 0;
    }

    public boolean deleteStunt(Stunt selectedStunt) {
        return db.delete(TABLE_STUNTS, COLUMN_STUNT_ID + "=" + selectedStunt.stuntId, null) > 0;
    }

    public void insertStunt(Stunt stunt)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUNT_NAME, stunt.stuntName);
        values.put(COLUMN_STUNT_ID, stunt.stuntId);
        db.insert(TABLE_STUNTS, null, values);
    }

    private Stunt cursorToStunt(Cursor cursor)
    {
        Stunt stunt = new Stunt();
        stunt.stuntId = cursor.getInt(0);
        stunt.stuntName = cursor.getString(1);
        return stunt;
    }
}

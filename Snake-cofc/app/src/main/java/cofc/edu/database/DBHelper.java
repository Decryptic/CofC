package cofc.edu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import cofc.edu.scores.GameScores;


public class DBHelper extends SQLiteOpenHelper
{
    //creates instance variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores.database";
    public static final String TABLE_SCORES = "scores";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_NAME = "name";

    private final String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORES + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_SCORE + " INTEGER"
            + ")";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void addScores(GameScores scores)
    {
        ContentValues scoreVal = new ContentValues();
        scoreVal.put(COLUMN_NAME, scores.getName());
        scoreVal.put(COLUMN_SCORE, scores.getScores());
        SQLiteDatabase database = this.getReadableDatabase();
        database.insert(TABLE_SCORES, null, scoreVal);
        database.close();
    }

    public ArrayList<GameScores> getAllScores()
    {
        ArrayList<GameScores> scores = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SCORES + " ORDER BY " + COLUMN_SCORE + " DESC";

        SQLiteDatabase dbase = getWritableDatabase();
        Cursor cursor = dbase.rawQuery(query, null);

        GameScores gameScores = null;

        if (cursor.moveToFirst())
        {
            do
            {
                gameScores = new GameScores();
                gameScores.setId(Integer.parseInt(cursor.getString(0)));
                gameScores.setName(cursor.getString(1));
                gameScores.setScores(Integer.parseInt(cursor.getString(2)));

                scores.add(gameScores);
            } while (cursor.moveToNext());
            cursor.close();
        }
        dbase.close();
        return scores;
    }
}


package com.cdoss.fantasydrafttool;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.cdoss.fantasydrafttool/databases/";
	private static String DB_NAME = "FFDT.db";
	private SQLiteDatabase myDatabase;
	private final Context myContext;

	public static final String TABLE_NAME = "top250players";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_RANK = "ranks";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_POS = "position";
	public static final String COLUMN_TEAM = "team";
	public static final String COLUMN_BYEWEEK = "byeweek";
	public static final String COLUMN_STATSB4 = "statslastseason";
	public static final String COLUMN_STATSPROJECTED = "projectedstats";
	public static final String COLUMN_DRAFTGROUP = "draftgroup";
	public static final String COLUMN_POSRANK = "PosRank";

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DatabaseHelper(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}
	}

	public void resetDatabase() throws IOException {
		this.getReadableDatabase();
		try {
			copyDataBase();
		} catch (IOException e) {
			throw new Error("Error copying database");
		}
	}

	// public void createDataBase() throws IOException
	// {
	// if (checkDataBase())
	// return;
	// myDatabase = getReadableDatabase();
	// try
	// {
	// copyDataBase();
	// return;
	// }
	// catch (IOException localIOException)
	// {
	// }
	// throw new Error("Error copying database");
	// }

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.
			Log.e("SqlHelper", "database not found");

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDatabase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if (myDatabase != null)
			myDatabase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

	public Cursor getCursor(String positionString, String draftString, String rankDelimiter) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		String[] asColumnToReturn = new String[] { COLUMN_ID, COLUMN_RANK,
				COLUMN_NAME, COLUMN_POS, COLUMN_TEAM, COLUMN_BYEWEEK,
				COLUMN_DRAFTGROUP, COLUMN_STATSB4, COLUMN_STATSPROJECTED };
		// make sure you get your search by string pass correctly!
		String filter = positionString + " AND " + draftString + " AND " + rankDelimiter;
		Cursor mCursor = queryBuilder.query(myDatabase, asColumnToReturn,
				filter, null, null, null, COLUMN_RANK);
		// myDatabase = DatabaseHelper.this.getReadableDatabase();
		// String sql =
		// "SELECT * FROM top250players WHERE Position='RB' ORDER BY Rank ";
		// Cursor mCursor =
		// myDatabase.rawQuery("SELECT * FROM top250players WHERE draftgroup=3",
		// null);
		return mCursor;
	}
	
	public Cursor getCursor2(String positionString, String draftString, String rankDelimiter) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		String[] asColumnToReturn = new String[] { COLUMN_ID, COLUMN_POSRANK,
				COLUMN_NAME, COLUMN_POS, COLUMN_TEAM, COLUMN_BYEWEEK,
				COLUMN_DRAFTGROUP, COLUMN_STATSB4, COLUMN_STATSPROJECTED, COLUMN_RANK };
		// make sure you get your search by string pass correctly!
		String filter = positionString + " AND " + draftString + " AND " + rankDelimiter;
		Cursor mCursor = queryBuilder.query(myDatabase, asColumnToReturn,
				filter, null, null, null, COLUMN_POSRANK);
		// myDatabase = DatabaseHelper.this.getReadableDatabase();
		// String sql =
		// "SELECT * FROM top250players WHERE Position='RB' ORDER BY Rank ";
		// Cursor mCursor =
		// myDatabase.rawQuery("SELECT * FROM top250players WHERE draftgroup=3",
		// null);
		return mCursor;
	}

	public String getPlayer(Cursor c) {
		String player = c.getString(1) + " | " + c.getString(2) + " | "
				+ c.getString(4) + " | " + c.getString(3) + " | "
				+ c.getString(5);
		return player;
		// return (c.getString(2));
	}

	public String getOldStats(Cursor c) {
		// TODO Auto-generated method stub
		String stats = "Stats Last Season: " + c.getString(7);
		return stats;
	}

	public String getNewStats(Cursor c) {
		String stats = "Projected Stats\t : " + c.getString(8);//getString(8) corresponds to the array in the getCursor2 method
		return stats;
	}

	public void updatePlayerDraftList(String rowId, int draftGroup) {

		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(COLUMN_DRAFTGROUP, draftGroup);

		myDatabase
				.update(TABLE_NAME, cvUpdate, COLUMN_RANK + "=" + rowId, null);
	}
}
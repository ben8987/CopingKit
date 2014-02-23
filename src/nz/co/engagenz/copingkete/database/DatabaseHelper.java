package nz.co.engagenz.copingkete.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "Ketes";
	
	private final static int DATABASE_VERSION = 1;
	
	public static final String DATABASE_TABLE = "testkete";

	private static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" (_id integer primary key autoincrement, "
		+ "title text unique not null, date text not null, link text not null, img text not null);";
	
	public DatabaseHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(DATABASE_CREATE);
		db.execSQL("create table personalkete (_id integer primary key autoincrement,"
				+ "title text unique not null, date text not null, link text not null, img text not null);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS testkete");
		onCreate(db);
	}

}

package nz.co.engagenz.copingkete.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import nz.co.engagenz.copingkete.MainActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * 数据库操作对�?
 * @author xuzhenqin
 *
 */
public class KetesDbAdapter {
//	private static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" (_id integer primary key autoincrement, "
//			+ "title text not null, date text not null, link text not null, img text not null);";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DATE = "date";
	public static final String KEY_LINK = "link";
	public static final String KEY_IMG = "img";
	public static final String KEY_ROWID = "_id";
	

	private DatabaseHelper databaseHelper;
	
	private Context context;
	
	private SQLiteDatabase sqliteDatabase;
	
	public KetesDbAdapter(Context context)
	{
		this.context = context;
	}
	
	
	/**
	 * 打开数据库连�?
	 */
	public void open()
	{
		databaseHelper = new DatabaseHelper(context);
		
		try
		{
			sqliteDatabase = databaseHelper.getWritableDatabase();
		}catch(SQLiteException ex)
		{
			sqliteDatabase = databaseHelper.getReadableDatabase();
		}
	}
	
	/**
	 * 关闭数据库连�?
	 */
	public void close()
	{
		sqliteDatabase.close();
	}
	
	/**
	 * 向数据库表中插入�?��数据
	 * @param title 字段�?
	 * @param body 字段�?
	 */
	public long insertRecord(String title, String date, String link, String img, String table)
	{
		ContentValues content = new ContentValues();
		content.put(KEY_TITLE, title);
		content.put(KEY_DATE, date);
		content.put(KEY_LINK, link);
		content.put(KEY_IMG, img);
//		
//		Calendar calendar = Calendar.getInstance();
//		String created = calendar.get(Calendar.YEAR) + "�?
//				+ calendar.get(Calendar.MONTH) + "�?
//				+ calendar.get(Calendar.DAY_OF_MONTH) + "�?
//				+ calendar.get(Calendar.HOUR_OF_DAY) + "�?
//				+ calendar.get(Calendar.MINUTE) + "�?;
//		content.put(KEY_IMG, created);
		
		//content为插入表中的�?��记录，类似与HASHMAP，是以键值对形式存储�?
		//insert方法第一参数：数据库表名，第二个参数如果CONTENT为空时则向表中插入一个NULL,第三个参数为插入的内�?
		return sqliteDatabase.insert(table, null, content);
	}
	
	/**
	 * 删除表中符合条件的记�?
	 * @param rowId 删除条件
	 * @return 是否删除成功
	 */
	public boolean deleteRecord(String table, long rowId)
	{
		//delete方法第一参数：数据库表名，第二个参数表示条件语句,第三个参数为条件�?的替代�?
		//返回值大�?表示删除成功
		return sqliteDatabase.delete(table, KEY_ROWID +"="+rowId , null)>0;
	}
	
	/**
	 * 查询全部表记�?
	 * @return 返回查询的全部表记录
	 */
	public Cursor getAllRecords(String table)
	{
		//查询表中满足条件的所有记�?
		return sqliteDatabase.query(table, new String[] { KEY_ROWID, KEY_TITLE,
				KEY_DATE, KEY_LINK, KEY_IMG }, null, null, null, null, KEY_TITLE);
	}
	
	public ArrayList<HashMap<String, String>> getAllRecordsAsArrayList(String table){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		Cursor c = getAllRecords(table);
		while (c.moveToNext())
	    {
			
	        try
	        {
	        	HashMap<String, String> map = new HashMap<String, String>();
		        map.put("title", c.getString(c.getColumnIndex("title")));
		        map.put("pubDate", c.getString(c.getColumnIndex("date")));
		        map.put("link", c.getString(c.getColumnIndex("link")));
		        map.put("encoded", c.getString(c.getColumnIndex("img")));
	        	list.add(map);
	        }
	        catch (Exception e) {
	            Log.e("tag", "Error " + e.toString());
	        }

	    }
		
		return list;
		
	}

	/**
	 * 查询带条件的记录
	 * @param rowId 条件�?
	 * @return 返回查询结果
	 * @throws SQLException 查询时异常抛�?
	 */
	public Cursor getRecord(String table, long rowId) throws SQLException {

		//查询表中条件值为rowId的记�?
		Cursor mCursor =
			sqliteDatabase.query(true, table, new String[] { KEY_ROWID, KEY_TITLE,
					KEY_DATE, KEY_LINK, KEY_IMG }, KEY_ROWID + "=" + rowId, null, null,
				null, null, null);
		
		//mCursor不等于null,将标识指向第�?��记录
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	/**
	 * 更新数据�?
	 * @param rowId 行标�?
	 * @param title 内容
	 * @param body 内容
	 * @return 是否更新成功
	 */
//	public boolean updateDiary(long rowId, String title, String body) {
//		ContentValues args = new ContentValues();
//		args.put(KEY_TITLE, title);
//		args.put(KEY_DATE, body);
//		Calendar calendar = Calendar.getInstance();
//		String created = calendar.get(Calendar.YEAR) + "�?
//				+ calendar.get(Calendar.MONTH) + "�?
//				+ calendar.get(Calendar.DAY_OF_MONTH) + "�?
//				+ calendar.get(Calendar.HOUR_OF_DAY) + "�?
//				+ calendar.get(Calendar.MINUTE) + "�?;
//		args.put(KEY_IMG, created);
//
//		//第一个参�?数据库表�?第二个参数更新的内容,第三个参数更新的条件,第四个参数条件带?号的替代�?
//		return sqliteDatabase.update(databaseHelper.DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
//	}


}
